package com.hiiragi.library.exceptions;

public class LoanLimitExceededExcetion extends RuntimeException{
    public LoanLimitExceededExcetion(String message){
        super(message);
    }
}
