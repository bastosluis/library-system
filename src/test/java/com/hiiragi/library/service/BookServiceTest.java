package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.repository.BookRepository;
import static com.hiiragi.library.util.MockedObjects.createBook;
import static com.hiiragi.library.util.MockedObjects.createSession;

public class BookServiceTest {
        private BookRepository bookRepo;
        private BookService bookService;
        private AuthorizationService authorizationService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        bookRepo = new BookRepository();
        authorizationService = new AuthorizationService(createSession(UserRole.MEMBER));
        bookService = new BookService(bookRepo, authorizationService);
    }

    void changeSessionToAdmin(){
        authorizationService.setSession(createSession(UserRole.ADMIN));
    }

    @Test
    void shouldAddBook(){
        changeSessionToAdmin();
        Book book = createBook();
        bookService.add(book);
        assertEquals(book, bookService.findById(book.getId()).get());
    }

    @Test
    void shouldGetAvailableCopy(){
        Book book = createBook();
        BookCopy copy = new BookCopy(book.getId());
        book.addCopy(copy);
        assertEquals(
            copy,
            bookService.getAvailableCopy(book).orElseThrow()
        );
    }

    @Test
    void shouldRemoveBookById(){
        changeSessionToAdmin();
        Book book = createBook();
        bookService.add(book);
        assertDoesNotThrow(() -> bookService.removeById(book.getId()));
        assertTrue(bookService.findAll().isEmpty());
    }

    @Test
    void shouldNotAllowRemoveBook(){
        Book book = createBook();
        bookRepo.save(book);
        assertThrows(
            UnauthorizedException.class,
            () -> bookService.removeById(book.getId())
        );
        assertFalse(bookService.findAll().isEmpty());
    }
}
