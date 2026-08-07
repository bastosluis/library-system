package com.hiiragi.library.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.UserRole;
import static com.hiiragi.library.util.MockedNames.EMAIL;
import static com.hiiragi.library.util.MockedNames.LOGIN;
import static com.hiiragi.library.util.MockedNames.PASSWORD;
import static com.hiiragi.library.util.MockedNames.PHONE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;

public class UserTest {
    @Test
    void shouldCreateUserWithCorrectAttributes(){
        int maxLoans = 3;
        User user = new User(USER_NAME, EMAIL, PHONE, true, maxLoans, UserRole.MEMBER, LOGIN, PASSWORD);
        assertEquals(USER_NAME, user.getName());
        assertEquals(USER_NAME, user.getName());
        assertEquals(EMAIL, user.getEmail());
        assertEquals(PHONE, user.getPhone());
        assertTrue(user.isActive());
        assertEquals(maxLoans, user.getMaxLoans());
        assertEquals(UserRole.MEMBER, user.getRole());
        assertEquals(LOGIN, user.getLogin());
        assertEquals(PASSWORD, user.getPassword());
    }

    /*
    To do tests: 
    @Test
    void shouldForbidMemberUserFromAdminAccess(){
    }
    
    @Test
    void shouldForbidLibraryUserFromAdminAccess(){
    }

    
    */
}
