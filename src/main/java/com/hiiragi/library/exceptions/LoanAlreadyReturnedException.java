package com.hiiragi.library.exceptions;

public class LoanAlreadyReturnedException extends Exception {
    public LoanAlreadyReturnedException(Long id){
        super("Loan has been already returned: id "+id);
    }
}
