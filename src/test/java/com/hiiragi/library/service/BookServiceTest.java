package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.Book;
import com.hiiragi.library.repository.BookRepository;
import static com.hiiragi.library.util.MockedObjects.createBook;

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
        assertEquals(book, bookService.findById(book.getId()).get());
    }

    @Test
    void shouldGetAvailableCopy(){
        Book book = createBook();
        bookService.add(book);
        assertNotNull(bookService.getAvailableCopy(book));
    }
}
