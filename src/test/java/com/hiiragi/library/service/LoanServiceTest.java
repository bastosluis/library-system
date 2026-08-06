package com.hiiragi.library.service;

import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.Loan;
import com.hiiragi.library.repository.LoanRepository;
import static com.hiiragi.library.util.MockedObjects.createLoan;

public class LoanServiceTest {
        private LoanRepository loanRepo;
        private LoanService loanService;

    @BeforeEach
    void setUp(){
        loanRepo = new LoanRepository();
        loanService = new LoanService(loanRepo);
    }

    @Test
    void shouldAddLoan(){
        Loan loan = createLoan();
        loanService.add(loan);
        
        assertTrue(loanService.findAll().contains(loan));
    }
    
    @Test
    void shouldRemoveLoanById(){
        Loan loan = createLoan();
        loanService.add(loan);
        loanService.removeById(loan.getId());
        
        assertTrue(loanService.findAll().isEmpty());
    }
}
