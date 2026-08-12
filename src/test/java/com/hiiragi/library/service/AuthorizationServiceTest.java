package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.InactiveUserException;
import com.hiiragi.library.exceptions.LoanLimitExceededExcetion;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.User;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class AuthorizationServiceTest {
    private Session session;
    private User user;
    private AuthorizationService authorizationService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        session = new Session();
        User user = createUser();
        session.login(user);
        authorizationService = new AuthorizationService(session);
    }

    @Test
    void shouldRequireMemberRole(){
        assertDoesNotThrow(() -> authorizationService.requireRole(UserRole.MEMBER));
    }

    @Test
    void shouldNotAcceptMemberRole(){
        assertThrows(UnauthorizedException.class, () -> authorizationService.requireRole(UserRole.ADMIN, UserRole.LIBRARIAN));
    }

    @Test
    void shouldRequireActiveUser(){
        assertDoesNotThrow(() -> authorizationService.requireActive());
    }
    
    @Test
    void shouldNotAcceptInactiveUser(){
        user.setActive(false);
        assertThrows(InactiveUserException.class, () -> authorizationService.requireActive());
    }

    @Test
    void shouldRequireLoansUnderLimit(){
        assertDoesNotThrow(() -> authorizationService.requireLoanUnderLimit());
    }

    @Test
    void shouldNotAcceptLoansOverLimit(){
        user.decreaseLoan();
        assertThrows(LoanLimitExceededExcetion.class, () -> authorizationService.requireLoanUnderLimit());
    }
}
