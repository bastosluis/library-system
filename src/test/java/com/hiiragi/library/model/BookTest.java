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
    void shouldCreateBookWithCorrectAttributes() {
        Author author = createAuthor();
        Category category = createCategory();
        Book book = new Book(BOOK_TITLE, ISBN, BOOK_DESCRIPTION, YEAR, author, category);
        
        assertEquals(BOOK_TITLE, book.getTitle());
        assertEquals(ISBN, book.getIsbn());
        assertEquals(BOOK_DESCRIPTION, book.getDescription());
        assertEquals(YEAR, book.getPublicationYear());
        assertEquals(author, book.getAuthor());
        assertEquals(category, book.getCategory());
    }

    @Test
    void shouldGiveCorrectString(){
        final Book book = createBook();
        String test = String.format("Title: %s%nId: null%nISBN: %s%nDescription: %s%nYear of publication: %s%nAuthor: %s%nCategory: %s%nCopies: 0", 
                            BOOK_TITLE, 
                            ISBN, 
                            BOOK_DESCRIPTION, 
                            YEAR, 
                            createAuthor().getName(), 
                            createCategory().getName());
        assertEquals(test, book.toString(), "toString method in Book class returned a string different than expected");
    }
    
    @Test
    void shouldAddThenRemoveCopy(){
        final Book book = createBook();
        BookCopy copy1 = new BookCopy(0L);
        // BookCopy copy2 = new BookCopy(2, 0);
        book.addCopy(copy1);
        assertTrue(book.getCopies().contains(copy1), "addCopy method did not successfuly add a BookCopy object");
        book.removeCopy(copy1);
        assertTrue(!book.hasCopies(), "addCopy method did not successfuly remove a BookCopy object");
    }
}

