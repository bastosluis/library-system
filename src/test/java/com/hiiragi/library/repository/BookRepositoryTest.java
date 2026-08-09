package com.hiiragi.library.repository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.exceptions.DuplicateBookException;
import com.hiiragi.library.model.Book;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedObjects.createBook;

public class BookRepositoryTest {
    
    private BookRepository bookRepo;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        bookRepo = new BookRepository();
    }

    @Test
    void shouldSaveBook(){
        Book book = createBook();
        // final String errorMessage = "Saved book is different than expected.";
        // BookRepository bookRepo = new BookRepository();
        bookRepo.save(book);
        assertEquals(book, bookRepo.findByTitle(book.getTitle()).get());
    }

    @Test
    void shouldNotSaveDuplicateBook(){
        Book book = createBook();
        bookRepo.save(book);
        assertThrows(DuplicateBookException.class, () -> bookRepo.save(book));
    }

    @Test
    void shouldSaveMultipleBooks(){
        Book book1 = createBook();
        Book book2 = createBook();
        book2.setTitle("different title");
        book2.setIsbn("different isbn");
        bookRepo.save(book1);
        bookRepo.save(book2);
        assertEquals(2, bookRepo.findAll().size());    
    }

    @Test
    void shouldReturnBookWhenIdExists(){
        Book book = createBook();
        bookRepo.save(book);
        assertEquals(book.getId(), bookRepo.findById(book.getId()).get().getId());
    }

    @Test
    void shouldReturnAllBooks(){
        Book book = createBook();
        bookRepo.save(book);
        assertEquals(book, bookRepo.findAll().getFirst());
    }

    @Test
    void shouldDeleteBook(){
        Book book = createBook();
        bookRepo.save(book);
        bookRepo.delete(book);
        assertTrue(bookRepo.isEmpty());
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
    void shouldDeleteOnlySpecifiedBook(){
        for (int i = 0; i < 10; i++){
            bookRepo.save(createBook(BOOK_TITLE+" "+i, ISBN+" "+i));
        }
        Book book = bookRepo.findById(5L).get();
        bookRepo.delete(book);
        assertEquals(Optional.empty(), bookRepo.findById(5L));
    }
    
    @Test
    void shouldIncreaseSizeAfterSave(){
        Book book = createBook();
        bookRepo.save(book);
        assertEquals(1, bookRepo.findAll().size());
    }

    @Test
    void shouldDecreaseSizeAfterDelete(){
        Book book = createBook();
        bookRepo.save(book);
        bookRepo.deleteById(book.getId());
        assertEquals(0, bookRepo.findAll().size());
    }

    @Test
    void shouldAddCopy(){
        Book book = createBook();
        bookRepo.save(book);
        bookRepo.addCopy(book);
        assertEquals(2, book.getCopies().size());
    }

    @Test
    void shouldAddMultipleCopies(){
        Book book = createBook();
        bookRepo.save(book);
        for (int i = 0; i < 9; i++) {
            bookRepo.addCopy(book);
        }
        assertEquals(10, book.getCopies().size());
    }
}
