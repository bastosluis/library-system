package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.UserRepository;
import static com.hiiragi.library.util.MockedNames.EMAIL;
import static com.hiiragi.library.util.MockedNames.PHONE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class UserServiceTest {
        private UserRepository userRepo;
        private UserService userService;

    @BeforeEach
    void setUp(){
        userRepo = new UserRepository();
        userService = new UserService(userRepo);
    }

    @Test
    void shouldAddUser(){
        User user = createUser();
        userService.add(user);
        assertEquals(user, userService.findById(user.getId()));
    }
    
    @Test
    void shouldDeactivateUser(){
        User user = createUser();
        userService.add(user);
        assertTrue(userService.deactivate(user.getId()));
        assertFalse(user.isActive());
    }

    @Test
    void shouldCreateUser(){
        User user = userService.createUser(USER_NAME, EMAIL, PHONE, UserRole.MEMBER);
        assertEquals(user, userService.findById(user.getId()));
    }

    @Test
    void shouldRejectInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, "invalid@", PHONE, UserRole.MEMBER));
    }

    @Test
    void shouldRejectEmptyEmail(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, "", PHONE, UserRole.MEMBER));
    }

    @Test
    void shouldRejectInvalidPhone(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, EMAIL, "1234phone", UserRole.MEMBER));
    }

    @Test
    void shouldRejectEmptyPhone(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, EMAIL,"", UserRole.MEMBER));
    }

}
