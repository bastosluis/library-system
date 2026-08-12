package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.UserRepository;
import static com.hiiragi.library.util.MockedNames.EMAIL;
import static com.hiiragi.library.util.MockedNames.LOGIN;
import static com.hiiragi.library.util.MockedNames.PASSWORD;
import static com.hiiragi.library.util.MockedNames.PHONE;
import static com.hiiragi.library.util.MockedNames.USER_NAME;
import static com.hiiragi.library.util.MockedObjects.createSession;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class UserServiceTest {
        private UserRepository userRepo;
        private UserService userService;
        private AuthorizationService authorizationService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        userRepo = new UserRepository();
        authorizationService = new AuthorizationService(createSession(UserRole.MEMBER));
        userService = new UserService(userRepo, authorizationService);
    }

    void changeSessionToAdmin(){
        authorizationService.setSession(createSession(UserRole.ADMIN));
    }

    @Test
    void shouldAddUser(){
        User user = createUser();
        userService.add(user);
        assertEquals(user, userService.findById(user.getId()).get());
    }
    
    @Test
    void shouldDeactivateUser(){
        changeSessionToAdmin();
        User user = createUser();
        userService.add(user);
        assertDoesNotThrow(() -> userService.deactivate(user.getId()));
        assertFalse(user.isActive());
    }

    @Test
    void shouldCreateUser(){
        User user = userService.createUser(USER_NAME, EMAIL, PHONE, UserRole.MEMBER, LOGIN, PASSWORD);
        assertEquals(user, userService.findById(user.getId()).get());
    }

    @Test
    void shouldRejectInvalidEmail(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, "invalid@", PHONE, UserRole.MEMBER, LOGIN, PASSWORD));
    }

    @Test
    void shouldRejectEmptyEmail(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, "", PHONE, UserRole.MEMBER, LOGIN, PASSWORD));
    }

    @Test
    void shouldRejectInvalidPhone(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, EMAIL, "1234phone", UserRole.MEMBER, LOGIN, PASSWORD));
    }

    @Test
    void shouldRejectEmptyPhone(){
        assertThrows(IllegalArgumentException.class, () -> userService.createUser(USER_NAME, EMAIL,"", UserRole.MEMBER, LOGIN, PASSWORD));
    }

}
