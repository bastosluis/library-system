package com.hiiragi.library.exceptions;

public class InvalidUserRoleException extends RuntimeException{
    public InvalidUserRoleException(String message){
        super(message);
    }
}
