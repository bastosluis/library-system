package com.hiiragi.library.repository;

import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Repository;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.model.Loan;

@Repository
public class LoanRepository 
        extends InMemoryRepository<Loan>{

    public List<Loan> findByUserId(Long id){
        return this.entities.stream()
                            .filter((loan) -> Objects.equals(loan.getUserId(), id))
                            .toList();
    }

    public List<Loan> findByBookId(Long id){
        return this.entities.stream()
                            .filter((loan) -> Objects.equals(loan.getBookId(), id))
                            .toList();

    }

    public List<Loan> findByStatus(LoanStatus status){
        return this.entities.stream()
                            .filter((loan) -> Objects.equals(loan.getStatus(), status))
                            .toList();
    }
}
