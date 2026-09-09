package com.hiiragi.library.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.repository.LoanRepository;

@Service
public class LoanService extends BaseService<Loan, LoanRepository>{

    public LoanService(LoanRepository repository) {
        super(repository);
    }

    public Loan add(Loan loan){
        this.repository.save(loan);
        return loan;
    }

    @Override
    public void removeById(Long id){
        try {
            super.removeById(id);
        } catch (NotFoundException e) {
            throw new LoanNotFoundException(id);
        }
    }
    
    public void updateLoans(){
        this.repository.findAll().forEach(loan -> {
            if (loan != null) loan.update();
        });
    }

    public List<Loan> findByUserId(Long id){
        return this.repository.findByUserId(id);
    }

    public List<Loan> findByBookId(Long id){
        return this.repository.findByBookId(id);
    }

    public List<Loan> findByStatus(LoanStatus status){
        return this.repository.findByStatus(status);
    }
}
