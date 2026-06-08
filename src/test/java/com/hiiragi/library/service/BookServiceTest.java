package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.NoAvailableCopiesException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.BookRepository;
import static com.hiiragi.library.util.MockedObjects.createBook;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class BookServiceTest {
        private BookRepository bookRepo;
        private BookService bookService;

    @BeforeEach
    void setUp(){
        bookRepo = new BookRepository();
        bookService = new BookService(bookRepo);
    }

    @Test
    void shouldAddBook(){
        Book book = createBook();
        bookService.add(book);
        assertEquals(book, bookService.findById(book.getId()));
    }

    @Test
    void shouldBorrowBook(){
        Book book = createBook();
        User user = createUser();
        bookService.add(book);
        bookService.borrowBook(book.getTitle(), user);
        assertEquals(book.getId(), user.getBorrowedCopies().get(0).getBookId());
        assertEquals(BookStatus.BORROWED, book.getCopies().get(0).getStatus());
    }

    @Test
    void shouldGetAvailableCopy(){
        Book book = createBook();
        bookService.add(book);
        assertNotNull(bookService.getAvailableCopy(book));
    }

    @Test
    void shouldNotBorrowBookTwice(){
        Book book = createBook();
        User user = createUser();
        bookService.add(book);
        bookService.borrowBook(book.getTitle(), user);
        assertThrows(NoAvailableCopiesException.class, () -> bookService.borrowBook(book.getTitle(), user));
    }
}
