package com.hiiragi.library.repository;

import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.DuplicateBookException;
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

    public Optional<Book> findByIsbn(String isbn){
        for (Book book : entities) {
            if (book.getIsbn().equals(isbn)) {
                return Optional.of(book);
            }
        }
        return Optional.empty();
    }

    @Override
    public Book save(Book book) throws DuplicateBookException{
        return save(book, BookStatus.AVAILABLE);
    }
    
    public Book save(Book book, BookStatus status) throws DuplicateBookException{
            ensureNotDuplicate(book);
            super.save(book);
            addCopy(book, status);
            return book;
    }

    private void ensureNotDuplicate(Book book){
        if (findByTitle(book.getTitle()).isPresent()){
            throw new DuplicateBookException("Tried adding duplicate book with title: "+book.getTitle());
        }
        if (findByIsbn(book.getIsbn()).isPresent()){
            throw new DuplicateBookException("Tried adding duplicate book with ISBN: "+book.getIsbn());
        }
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
