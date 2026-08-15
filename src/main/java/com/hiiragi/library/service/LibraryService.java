package com.hiiragi.library.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.CopyNotFoundException;
import com.hiiragi.library.exceptions.LoanAlreadyReturnedException;
import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.exceptions.UserNotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.model.User;

public class LibraryService {

    private final BookService bookService;
    private final UserService userService;
    private final LoanService loanService;
    private final AuthorizationService authorizationService;

    public LibraryService(
            BookService bookService,
            UserService userService,
            LoanService loanService,
            AuthorizationService authorizationService) {

        this.bookService = bookService;
        this.userService = userService;
        this.loanService = loanService;
        loanService.updateLoans();
        this.authorizationService = authorizationService;
    }

    // =========================
    // Books
    // =========================

    public Optional<Book> findBookById(Long id) {
        return bookService.findById(id);
    }

    public List<Book> findAllBooks() {
        return bookService.findAll();
    }

    public Optional<Book> findBookByTitle(String title) {
        return bookService.findByTitle(title);
    }

    public Book addBook(Book book) {
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );

        return bookService.add(book);
    }

    public Book addBook(Book book, BookStatus status) {
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );

        return bookService.add(book, status);
    }

    public void removeBook(Long id) {
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );

        bookService.removeById(id);
    }

    public Optional<BookCopy> getAvailableCopy(Book book) {
        return bookService.getAvailableCopy(book);
    }

    // =========================
    // Users
    // =========================

    public Optional<User> findUserById(Long id) {
        return userService.findById(id);
    }

    public List<User> findAllUsers() {
        authorizationService.requireRole(UserRole.ADMIN);

        return userService.findAll();
    }

    public Optional<User> findUserByLogin(String login){
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );
        
        return userService.findByLogin(login);
    }

    public Optional<User> addUser(User user) {
        authorizationService.requireRole(UserRole.ADMIN);

        return userService.add(user);
    }

    public void removeUser(Long id) {
        authorizationService.requireRole(UserRole.ADMIN);

        userService.removeById(id);
    }

    public void deactivateUser(Long id) {
        authorizationService.requireRole(UserRole.ADMIN);

        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setActive(false);
    }

    public void activateUser(Long id) {
        authorizationService.requireRole(UserRole.ADMIN);

        User user = userService.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        user.setActive(true);
    }
    
    // =========================
    // Loans
    // =========================

    public Optional<Loan> findLoanById(Long id) {
        return loanService.findById(id);
    }

    public List<Loan> findAllLoans() {
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );

        return loanService.findAll();
    }

    public List<Loan> findLoansByUserId(Long id){
        return loanService.findByUserId(id);
    }

    public List<Loan> findLoansByBookId(Long id){
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );

        return loanService.findByBookId(id);
    }

    public List<Loan> findLoansByStatus(LoanStatus status){
        authorizationService.requireRole(
                UserRole.LIBRARIAN,
                UserRole.ADMIN
        );
        
        return loanService.findByStatus(status);
    }

    // =========================
    // Business logic
    // =========================

    public void borrowBook(
            String title,
            Long userId,
            LocalDate dueDate) {

        authorizationService.requireActive();
        authorizationService.requireLoanUnderLimit();

        User user = userService.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Optional<Book> book = bookService.findByTitle(title);
        
        if (book.isEmpty()) {
            throw new BookNotFoundException(title);
        }

        Book foundBook = book.get();

        Optional<BookCopy> optionalCopy =
                bookService.getAvailableCopy(foundBook);

        if (optionalCopy.isEmpty()) {
            throw new NoAvailableCopiesException(title);
        }

        BookCopy copy = optionalCopy.get();

        copy.borrow();

        Loan loan = new Loan(
                copy.getBookId(),
                copy.getId(),
                user.getId(),
                dueDate
        );

        loanService.add(loan);
        user.increaseLoan();
    }

    public void returnBook(Long loanId)
            throws LoanNotFoundException,
                   BookNotFoundException,
                   LoanAlreadyReturnedException,
                   CopyNotFoundException {

        authorizationService.requireActive();

        Loan loan = loanService.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(loanId));

        if (loan.getStatus() == LoanStatus.RETURNED) {
            throw new LoanAlreadyReturnedException(loanId);
        }

        Book book = bookService.findById(loan.getBookId())
                .orElseThrow(
                        () -> new BookNotFoundException(loan.getBookId())
                );

        BookCopy copy = book.getCopy(loan.getBookCopyId())
                .orElseThrow(
                        () -> new CopyNotFoundException(loan.getBookCopyId())
                );

        User user = userService.findById(loan.getUserId())
                .orElseThrow(
                        () -> new UserNotFoundException(loan.getUserId())
                );

        copy.returnCopy();
        loan.markAsReturned(LocalDate.now());
        user.decreaseLoan();
    }

    public void printAllActiveLoansFromUser(Long userId){
        List<Loan> loans = findLoansByUserId(userId);
        if (loans.isEmpty()) {
            System.out.println("There are currently no loans yet.");
            return;
        }
        System.out.println("These are the books currently loaned:\n");
        
        for (Loan loan : loans){
            String title = findBookById(loan.getBookId()).orElseThrow().getTitle();
            System.out.println("- "+title+", Loan id: "+loan.getId()+
                                        "\n * Due Date: "+loan.getDueDate()+
                                        "\n * Loan Date: "+loan.getLoanDate()+
                                        "\n * Status: "+loan.getStatus());
        }
    }
}