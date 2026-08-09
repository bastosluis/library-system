package com.hiiragi.library.exceptions;

public class DuplicateBookException extends DuplicateEntityException{
    public DuplicateBookException(String message){
        super(message);
    }
}
