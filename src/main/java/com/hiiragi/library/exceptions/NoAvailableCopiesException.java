package com.hiiragi.library.exceptions;

public class NoAvailableCopiesException extends RuntimeException {
    public NoAvailableCopiesException(String title) {
        super("No available copies for: " + title);
    }
}
