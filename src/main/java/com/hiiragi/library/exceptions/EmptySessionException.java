package com.hiiragi.library.exceptions;

public class EmptySessionException extends RuntimeException{

    public EmptySessionException(String message) {
        super(message);    
    }

    public EmptySessionException() {
        super("Current session has no user logged in.");
    }
}
