package com.hiiragi.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import static com.hiiragi.library.util.MockedNames.BOOK_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedObjects.YEAR;
import static com.hiiragi.library.util.MockedObjects.createAuthor;
import static com.hiiragi.library.util.MockedObjects.createBook;
import static com.hiiragi.library.util.MockedObjects.createCategory;

public class BookTest {
    @Test
    void shouldDoBookCreation(){
        final Book BOOK = createBook();
        // assertEquals(book.getId(), ID);
        assertEquals(BOOK.getIsbn(), ISBN);
        assertEquals(BOOK.getDescription(), BOOK_DESCRIPTION);
        assertEquals(BOOK.getPublicationYear(), YEAR);
    }

    @Test
    void shouldGiveCorrectString(){
        final Book BOOK = createBook();
        String test = String.format("Title: %s%nId: null%nISBN: %s%nDescription: %s%nYear of publication: %s%nAuthor: %s%nCategory: %s%nCopies: 0", 
                            BOOK_TITLE, 
                            ISBN, 
                            BOOK_DESCRIPTION, 
                            YEAR, 
                            createAuthor().getName(), 
                            createCategory().getName());
        assertEquals(test, BOOK.toString(), "toString method in Book class returned a string different than expected");
    }
    
    @Test
    void shouldAddThenRemoveCopy(){
        final Book BOOK = createBook();
        BookCopy copy1 = new BookCopy(0L);
        // BookCopy copy2 = new BookCopy(2, 0);
        BOOK.addCopy(copy1);
        assertTrue(BOOK.getCopies().contains(copy1), "addCopy method did not successfuly add a BookCopy object");
        BOOK.removeCopy(copy1);
        assertTrue(!BOOK.hasCopies(), "addCopy method did not successfuly remove a BookCopy object");
    }
}

