package com.hiiragi.library.exceptions;

public class InvalidBookRemovalException extends InvalidRemovalException{
    public InvalidBookRemovalException(){
        super("The book had pending loans to be returned.");
    }
    public InvalidBookRemovalException(String title){
        super("The \""+title+"\" book had pending loans to be returned.");
    }
    public InvalidBookRemovalException(Long id){
        super("The book (id: "+id+") had pending loans to be returned.");
    }
}
