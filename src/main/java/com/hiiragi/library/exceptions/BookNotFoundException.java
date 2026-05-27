package com.hiiragi.library.exceptions;

public class BookNotFoundException extends RuntimeException {
    public BookNotFoundException(String title) {
        super("Book not found: " + title);
    }
}
