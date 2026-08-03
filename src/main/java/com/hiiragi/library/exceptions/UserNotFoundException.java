package com.hiiragi.library.exceptions;

public class UserNotFoundException extends NotFoundException{
    public UserNotFoundException(String login) {
        super("User not found: " + login);
    }

    public UserNotFoundException(Long id) {
        super("User not found: id " + id);
    }
}