package com.hiiragi.library.exceptions;

public class InvalidUserRemovalException extends InvalidRemovalException {
    public InvalidUserRemovalException(){
        super("The user had pending loans to be returned.");
    }
    public InvalidUserRemovalException(String login){
        super("The \""+login+"\" user had pending loans to be returned.");
    }
    public InvalidUserRemovalException(Long id){
        super("The user (id: "+id+") had pending loans to be returned.");
    }
}
