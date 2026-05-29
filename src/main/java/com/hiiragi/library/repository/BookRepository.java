package com.hiiragi.library.repository;

import com.hiiragi.library.model.Book;

public class BookRepository
        extends InMemoryRepository<Book> {

    public Book findByTitle(String title) {
        for (Book book : entities) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return book;
            }
        }
        return null;
    }
}
