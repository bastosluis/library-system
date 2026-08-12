package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.application.Session;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.exceptions.UnauthorizedException;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.repository.LoanRepository;
import static com.hiiragi.library.util.MockedObjects.createLoan;
import static com.hiiragi.library.util.MockedObjects.createSession;
import static com.hiiragi.library.util.MockedObjects.createUser;

public class LoanServiceTest {
        private LoanRepository loanRepo;
        private LoanService loanService;
        private AuthorizationService authorizationService;

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        loanRepo = new LoanRepository();
        Session session = new Session();
        session.login(createUser());
        authorizationService = new AuthorizationService(session);
        loanService = new LoanService(loanRepo, authorizationService);
    }

    void changeSessionToAdmin(){
        authorizationService.setSession(createSession(UserRole.ADMIN));
    }

    @Test
    void shouldAddLoan(){
        Loan loan = createLoan();
        loanService.add(loan);
        
        assertTrue(loanService.findAll().contains(loan));
    }
    
    @Test
    void shouldRemoveLoanById(){
        changeSessionToAdmin();
        Loan loan = createLoan();
        loanService.add(loan);

        assertDoesNotThrow(() -> loanService.removeById(loan.getId()));
        assertTrue(loanService.findAll().isEmpty());
    }

    @Test
    void shouldNotAllowRemoveLoanById(){
        Loan loan = createLoan();
        loanService.add(loan);
        
        assertThrows(UnauthorizedException.class, () -> loanService.removeById(loan.getId()));
        assertFalse(loanService.findAll().isEmpty());
    }
}
