package com.hiiragi.library.model;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import static com.hiiragi.library.util.MockedObjects.DUE_DATE;

public class LoanTest {

    @Test
    void shouldCreateLoanWithCorrectAttributes(){
        Long bookId = 1L;
        Long bookCopyId = 2L;
        Long userId = 3L;
        LocalDate dueDate = DUE_DATE;
        Loan loan = new Loan(bookId, bookCopyId, userId, dueDate);
        assertEquals(bookId, loan.getBookId());
        assertEquals(bookCopyId, loan.getBookCopyId());
        assertEquals(userId, loan.getUserId());
        assertEquals(dueDate, loan.getDueDate());
    }
}
