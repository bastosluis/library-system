package com.hiiragi.library.exceptions;

public class InvalidRemovalException extends RuntimeException{
    public InvalidRemovalException(){
        super();
    }

    public InvalidRemovalException(String message){
        super(message);
    }
}
