package com.hiiragi.library.repository;

import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;

public class BookRepository
        extends InMemoryRepository<Book> {

    public Optional<Book> findByTitle(String title) {
        for (Book book : entities) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    @Override
    public Book save(Book book){
        addCopy(book);
        return super.save(book);
    }

    public Book save(Book book, BookStatus status){
        addCopy(book, status);
        return super.save(book);
    }

    public void addCopy(Book book){
        book.addCopy(new BookCopy(nextId));
    }

    public void addCopy(Book book, BookStatus status){
        book.addCopy(new BookCopy(nextId, status));
    }
}
