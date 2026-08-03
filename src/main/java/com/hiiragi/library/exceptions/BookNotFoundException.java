package com.hiiragi.library.exceptions;

public class BookNotFoundException extends NotFoundException {
    public BookNotFoundException(String title) {
        super("Book not found: " + title);
    }

    public BookNotFoundException(Long id) {
        super("Book not found: id " + id);
    }
}
