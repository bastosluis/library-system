package com.hiiragi.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static com.hiiragi.library.util.MockedNames.BOOK_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedObjects.AUTHOR;
import static com.hiiragi.library.util.MockedObjects.CATEGORY;
import static com.hiiragi.library.util.MockedObjects.YEAR;

public class BookTest {
    @Test
    void shouldDoBookCreation(){
        Book book = new Book(
                BOOK_TITLE, 
                ISBN,
                BOOK_DESCRIPTION,
                YEAR,
                AUTHOR,
                CATEGORY
                );
        // assertEquals(book.getId(), ID);
        assertEquals(book.getIsbn(), ISBN);
        assertEquals(book.getDescription(), BOOK_DESCRIPTION);
        assertEquals(book.getPublicationYear(), YEAR);
    }
}
