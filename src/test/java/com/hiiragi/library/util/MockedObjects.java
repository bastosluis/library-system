package com.hiiragi.library.util;

import java.time.Year;

import com.hiiragi.library.model.Author;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.Category;
import static com.hiiragi.library.util.MockedNames.AUTHOR_NAME;
import static com.hiiragi.library.util.MockedNames.BOOK_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.CATEGORY_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.CATEGORY_TITLE;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedNames.NATIONALITY;

public final class MockedObjects {

    private MockedObjects() {
        // Prevent instantiation
    }
    public static final int ID = 0;
    public static final Year YEAR = Year.of(2026);
    public static final Author AUTHOR = new Author(AUTHOR_NAME, NATIONALITY);
    public static final Category CATEGORY = new Category(CATEGORY_TITLE, CATEGORY_DESCRIPTION);
    public static final Book BOOK = new Book(BOOK_TITLE, ISBN, BOOK_DESCRIPTION, YEAR, AUTHOR, CATEGORY);
}
