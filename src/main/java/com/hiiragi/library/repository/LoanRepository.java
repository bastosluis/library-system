package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.model.Loan;

public class LoanRepository 
        extends InMemoryRepository<Loan>{

    public List<Loan> findByUserId(Long id){
        List<Loan> loans = new ArrayList<>();
        for (Loan loan : this.entities) {
            if (loan.getUserId().equals(id)){
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> findByBookId(Long id){
        List<Loan> loans = new ArrayList<>();
        for (Loan loan : this.entities) {
            if (loan.getBookId().equals(id)){
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> findByStatus(LoanStatus status){
        List<Loan> loans = new ArrayList<>();
        for (Loan loan : this.entities) {
            if (loan.getStatus().equals(status)){
                loans.add(loan);
            }
        }
        return loans;
    }
}
