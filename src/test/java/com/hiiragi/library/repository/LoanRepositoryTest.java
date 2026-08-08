package com.hiiragi.library.repository;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.hiiragi.library.model.Loan;
import static com.hiiragi.library.util.MockedObjects.createLoan;

public class LoanRepositoryTest {
    
    private LoanRepository loanRepo; 

    @BeforeEach
    @SuppressWarnings("unused")
    void setUp(){
        loanRepo = new LoanRepository();
    }

    @Test
    void shouldSaveLoan(){
        Loan loan = createLoan();
        loanRepo.save(loan);
        assertTrue(loanRepo.findAll().contains(loan));
    }

    @Test
    void shouldSaveMultipleLoans(){
        Loan loan1 = createLoan();
        Loan loan2 = createLoan();
        loanRepo.save(loan1);
        loanRepo.save(loan2);

        assertEquals(2, loanRepo.findAll().size());
    }

    @Test
    void shouldDeleteLoan(){
        Loan loan = createLoan();
        loanRepo.save(loan);
        loanRepo.delete(loan);

        assertTrue(loanRepo.findAll().isEmpty());    
    }

    @Test
    void shouldDeleteLoanById(){
        Loan loan = createLoan();
        loanRepo.save(loan);
        loanRepo.deleteById(loan.getId());

        assertTrue(loanRepo.findAll().isEmpty());    
    }

    @Test
    void shouldDeleteOnlySpecifiedLoan(){
        Loan loan1 = createLoan();
        Loan loan2 = createLoan();
        Loan loan3 = createLoan();
        loanRepo.save(loan1);
        loanRepo.save(loan2);
        loanRepo.save(loan3);
        loanRepo.deleteById(loan1.getId());

        assertEquals(Optional.empty(), loanRepo.findById(loan1.getId()));
    }
}
