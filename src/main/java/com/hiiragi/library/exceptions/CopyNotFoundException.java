package com.hiiragi.library.exceptions;

public class CopyNotFoundException extends NotFoundException{
    public CopyNotFoundException(Long id){
        super("Loan not found: id "+id);
    }
}
