package com.hiiragi.library.service;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.repository.LoanRepository;
import static com.hiiragi.library.util.MockedObjects.createLoan;

public class LoanServiceTest {
        private LoanRepository loanRepo;
        private LoanService loanService;

    @BeforeEach
    @SuppressWarnings("unused")
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

    @Test
    void shouldUpdateLoans(){
        Loan lateLoan = createLoan();
        lateLoan.setDueDate(LocalDate.of(2000, 1,1));
        Loan activeLoan = createLoan();
        activeLoan.setDueDate(LocalDate.MAX);
        loanRepo.save(activeLoan);
        loanRepo.save(lateLoan);
        loanService.updateLoans();

        assertEquals(LoanStatus.ACTIVE, activeLoan.getStatus());
        assertEquals(LoanStatus.LATE, lateLoan.getStatus());
    }
}
