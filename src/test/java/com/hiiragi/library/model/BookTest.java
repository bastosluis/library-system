package com.hiiragi.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static com.hiiragi.library.util.MockedNames.BOOK_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedObjects.AUTHOR;
import static com.hiiragi.library.util.MockedObjects.BOOK;
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

    @Test
    void shouldGiveCorrectString(){
        String test = String.format("Title: %s%nId: null%nISBN: %s%nDescription: %s%nYear of publication: %s%nAuthor: %s%nCategory: %s%nCopies: 0", 
                            BOOK_TITLE, 
                            ISBN, 
                            BOOK_DESCRIPTION, 
                            YEAR, 
                            AUTHOR.getName(), 
                            CATEGORY.getName());
        assertEquals(test, BOOK.toString(), "toString method in Book class returned a string different than expected");
    }
    
    @Test
    void shouldAddThenRemoveCopy(){
        Book book = new Book(
                BOOK_TITLE, 
                ISBN,
                BOOK_DESCRIPTION,
                YEAR,
                AUTHOR,
                CATEGORY
                );
        BookCopy copy1 = new BookCopy(0L);
        // BookCopy copy2 = new BookCopy(2, 0);
        book.addCopy(copy1);
        assertTrue(book.getCopies().contains(copy1), "addCopy method did not successfuly add a BookCopy object");
        book.removeCopy(copy1);
        assertTrue(!book.hasCopies(), "addCopy method did not successfuly remove a BookCopy object");
    }
}

