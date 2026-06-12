package com.hiiragi.library.model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.enums.UserRole;
import static com.hiiragi.library.util.MockedNames.EMAIL;
import static com.hiiragi.library.util.MockedNames.PHONE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class UserTest {
    @Test
    void shouldCreateUserWithCorrectAttributes(){
        int maxLoans = 3;
        User user = new User(USER_NAME, EMAIL, PHONE, true, maxLoans, UserRole.MEMBER);
        assertEquals(USER_NAME, user.getName());
        assertEquals(USER_NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PHONE, user.getPhone());
        assertTrue(user.isActive());
        assertEquals(maxLoans, user.getMaxLoans());
        assertEquals(UserRole.MEMBER, user.getRole());
        assertNotNull(user.getBorrowedCopies());
    }

    @Test
    void shouldAddBorrowedBookCopy(){
        User user = createUser();
        BookCopy copy = new BookCopy(1L, BookStatus.BORROWED);

        user.addBorrowedBookCopy(copy);

        List<BookCopy> copyList = user.getBorrowedCopies();
        assertEquals(1L, copyList.get(0).getBookId());
        assertEquals(BookStatus.BORROWED, copyList.get(0).getStatus());
        assertEquals(1, user.getBorrowedCopies().size());
        assertTrue(user.getBorrowedCopies().contains(copy));
    }
    /*
    To do tests: 
    @Test
    void shouldForbidMemberUserFromAdminAccess(){
    }
    
    @Test
    void shouldForbidLibraryUserFromAdminAccess(){
    }

    @Test
    void 
    
    */
}
