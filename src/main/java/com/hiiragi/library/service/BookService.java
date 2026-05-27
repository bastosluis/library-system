package com.hiiragi.library.service;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;

public class BookService {
    private BookRepository bookRepository;

    public void borrowBook(String title, User user) {
        Book book = bookRepository.findByTitle(title);

        if (book == null) {
            throw new BookNotFoundException(title);
        }

        BookCopy copy = getAvailableCopy(book);

        if (copy == null) {
            throw new NoAvailableCopiesException(title);
        }

        copy.setStatus(BookStatus.BORROWED);
        user.addBorrowedBookCopy(copy);
    }

    public BookCopy getAvailableCopy(Book book){
        for (BookCopy copy : book.getCopies()) {
            if (copy.getStatus() == BookStatus.AVAILABLE) 
                return copy;
        }
        return null;
    }
}