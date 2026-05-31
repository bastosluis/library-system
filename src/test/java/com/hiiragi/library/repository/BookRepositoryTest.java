package com.hiiragi.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.Book;
import static com.hiiragi.library.util.MockedObjects.createBook;

public class BookRepositoryTest {
    
    private BookRepository bookRepo;

    @BeforeEach
    void setUp(){
        bookRepo = new BookRepository();
    }

    @Test
    void shouldSaveBook(){
        Book book = createBook();
        // final String errorMessage = "Saved book is different than expected.";
        // BookRepository bookRepo = new BookRepository();
        bookRepo.save(book);
        assertEquals(book, bookRepo.findByTitle(book.getTitle()));
    }

    @Test
    void shouldDeleteBookById(){
        Book book = createBook();
        // final String errorMessage = "Book is not deleted as expected.";
        // BookRepository bookRepo = new BookRepository();
        bookRepo.save(book);
        bookRepo.deleteById(book.getId());
        assertTrue(bookRepo.isEmpty());
    }

    @Test
    void shouldNotDeleteInexistentBookById() {
        // BookRepository bookRepo = new BookRepository();
        boolean deleted = bookRepo.deleteById(999L);
        assertFalse(deleted);
    }

    @Test
    void shouldIncreaseSizeAfterSave(){
        Book book = createBook();
        bookRepo.save(book);
        assertEquals(1, bookRepo.findAll().size());
    }

    @Test
    void shouldDecreaseSizeAfterSave(){
        Book book = createBook();
        bookRepo.save(book);
        bookRepo.deleteById(book.getId());
        assertEquals(0, bookRepo.findAll().size());
    }
}
