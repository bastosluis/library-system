package com.hiiragi.library.exceptions;

public class DuplicateUserException extends DuplicateEntityException{
    public DuplicateUserException(String message){
        super(message);
    }
}
