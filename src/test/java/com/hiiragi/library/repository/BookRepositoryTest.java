package com.hiiragi.library.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.Book;
import static com.hiiragi.library.util.MockedObjects.createBook;

public class BookRepositoryTest {
    
    final Book BOOK = createBook();
    @Test
    void shouldSaveBook(){
        final String ERROR_MESSAGE = "Saved book is different than expected.";
        BookRepository bookRepo = new BookRepository();
        bookRepo.save(BOOK);
        assertEquals(BOOK, bookRepo.findByTitle(BOOK.getTitle()), ERROR_MESSAGE);
    }
}
