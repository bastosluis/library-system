package com.hiiragi.library.util;

import java.time.Year;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.Author;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.Category;
import com.hiiragi.library.model.User;
import static com.hiiragi.library.util.MockedNames.AUTHOR_NAME;
import static com.hiiragi.library.util.MockedNames.BOOK_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.BOOK_TITLE;
import static com.hiiragi.library.util.MockedNames.CATEGORY_DESCRIPTION;
import static com.hiiragi.library.util.MockedNames.CATEGORY_TITLE;
import static com.hiiragi.library.util.MockedNames.EMAIL;
import static com.hiiragi.library.util.MockedNames.ISBN;
import static com.hiiragi.library.util.MockedNames.NATIONALITY;
import static com.hiiragi.library.util.MockedNames.LOGIN;
import static com.hiiragi.library.util.MockedNames.PASSWORD;
import static com.hiiragi.library.util.MockedNames.PHONE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;

public final class MockedObjects {

    private MockedObjects() {
        // Prevent instantiation
    }

    public static final Year YEAR = Year.of(2026);
    public static Author createAuthor() {
        return new Author(AUTHOR_NAME, NATIONALITY);
    }

    public static Category createCategory() {
        return new Category(CATEGORY_TITLE, CATEGORY_DESCRIPTION);
    }

    public static Book createBook() {
        return new Book(
            BOOK_TITLE,
            ISBN,
            BOOK_DESCRIPTION,
            Year.of(2026),
            createAuthor(),
            createCategory()
        );
    }

    public static User createUser() {
        return new User(USER_NAME,
            EMAIL,
            PHONE,
            true,
            1,
            UserRole.MEMBER,
            LOGIN,
            PASSWORD
            );
    }
}