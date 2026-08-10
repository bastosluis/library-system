package com.hiiragi.library.exceptions;

public class InactiveUserException extends RuntimeException{
    public InactiveUserException(String message){
        super(message);
    }
}
