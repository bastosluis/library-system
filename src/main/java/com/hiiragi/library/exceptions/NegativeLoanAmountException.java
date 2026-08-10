package com.hiiragi.library.exceptions;

public class NegativeLoanAmountException extends RuntimeException{
    public NegativeLoanAmountException(String message){
        super(message);
    }
}
