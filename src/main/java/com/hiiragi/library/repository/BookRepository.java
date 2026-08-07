package com.hiiragi.library.repository;

import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;

public class BookRepository
        extends InMemoryRepository<Book> {
    
    protected Long copyNextId = 1L;
    
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
        super.save(book);
        addCopy(book);
        return book;
    }
    
    public Book save(Book book, BookStatus status){
        super.save(book);
        addCopy(book, status);
        return book;
    }

    public void addCopy(Book book, BookStatus status){
        BookCopy copy = new BookCopy(book.getId(), status);
        copy.setId(copyNextId++);
        book.addCopy(copy);
    }
    
    public void addCopy(Book book){
        this.addCopy(book, BookStatus.AVAILABLE);
    }
}
