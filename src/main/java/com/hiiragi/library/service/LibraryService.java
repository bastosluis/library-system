package com.hiiragi.library.service;

import java.time.LocalDate;
import java.util.Optional;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.CopyNotFoundException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.model.User;

public class LibraryService {
    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;

    public LibraryService(BookService bookService, UserService userService, LoanService loanService) {
        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
    }

    public void borrowBook(String title, User user, LocalDate dueDate) {
        Optional<Book> book = bookService.findByTitle(title);

        if (book.isEmpty()) {
            throw new BookNotFoundException(title);
        }

        Optional<BookCopy> optionalCopy = bookService.getAvailableCopy(book.get());

        if (optionalCopy.isEmpty()) {
            throw new NoAvailableCopiesException(title);
        }
        
        BookCopy copy = optionalCopy.get();
        

        copy.borrow();
        loanService.add(new Loan(copy.getBookId(), copy.getId(), user.getId(), dueDate));
    }

    public void returnBook(Long loanId) throws LoanNotFoundException, BookNotFoundException, LoanAlreadyReturnedException, CopyNotFoundException{
        Loan loan = loanService.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException(loanId);
        }
        Book book = bookService.findById(loan.getBookId())
                    .orElseThrow(() -> new BookNotFoundException(loan.getBookId()));
                
        BookCopy copy = book.getCopy(loan.getBookCopyId())
            .orElseThrow(() -> new CopyNotFoundException(loan.getBookCopyId()));

        copy.returnCopy();
        loan.markAsReturned(LocalDate.now());
    }

    public BookService getBookService() {
        return bookService;
    }

    public UserService getUserService() {
        return userService;
    }

    public LoanService getLoanService() {
        return loanService;
    }
}
