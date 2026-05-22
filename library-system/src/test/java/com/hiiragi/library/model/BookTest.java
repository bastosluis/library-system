package com.hiiragi.library.model;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class BookTest {
    @Test
    void shouldDoBookCreation(){
        int id = 0;
        String bookTitle = "test book title";
        String isbn = "test ISBN";
        String description = "test description";
        Year year = Year.of(2026);
        Author author = new Author(id, "test author", "author's nationality");
        Category category = new Category(id, "test category", "category's description");
        Book book = new Book(
                bookTitle, 
                isbn,
                description,
                year,
                author,
                category
                );
        // assertEquals(book.getId(), id);
        assertEquals(book.getIsbn(), isbn);
        assertEquals(book.getDescription(), description);
        assertEquals(book.getPublicationYear(), year);
    }
}
