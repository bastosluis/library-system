package com.hiiragi.library.service;

import java.util.List;

import com.hiiragi.library.enums.LoanStatus;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.repository.LoanRepository;

public class LoanService extends BaseService<Loan, LoanRepository>{

    private AuthorizationService authorizationService;
    
    public LoanService(LoanRepository repository) {
        super(repository);
    }

    public void setAuthorizationService(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    public Loan add(Loan loan){
        this.repository.save(loan);
        return loan;
    }

    @Override
    public void removeById(Long id){
        authorizationService.requireRole(UserRole.ADMIN, UserRole.LIBRARIAN);
        super.removeById(id);
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
