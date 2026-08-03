package com.hiiragi.library.exceptions;
 
public class LoanNotFoundException extends NotFoundException{
    public LoanNotFoundException(Long id){
        super("Loan not found: id "+id);
    }
}
