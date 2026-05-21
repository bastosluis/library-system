package com.hiiragi.library.model;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    void bookCreationTest(){
        int id = 0;
        String bookTitle = "test book title";
        String isbn = "test ISBN";
        String description = "test description";
        Year year = Year.of(2026);
        Book book = new Book(id,
                bookTitle, 
                isbn,
                description,
                year);
        assertEquals(book.getId(), id);
        assertEquals(book.getIsbn(), isbn);
        assertEquals(book.getDescription(), description);
        assertEquals(book.getPublicationYear(), year);
    }
}
