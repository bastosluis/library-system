package com.hiiragi.library.repository;

import java.util.Objects;
import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.DuplicateBookException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;

public class BookRepository
        extends InMemoryRepository<Book> {
    
    private Long copyNextId = 1L;
    private Long authorNextId = 1L;
    private Long categoryNextId = 1L;
    
    public Optional<Book> findByTitle(String title) {
        return entities.stream()
                    .filter((book) -> Objects.equals(book.getTitle(), title))
                    .findAny();
    }

    public Optional<Book> findByIsbn(String isbn){
        return entities.stream()
                    .filter((book) -> Objects.equals(book.getIsbn(), isbn))
                    .findAny();
    }

    @Override
    public Book save(Book book) throws DuplicateBookException{
        return save(book, BookStatus.AVAILABLE);
    }
    
    public Book save(Book book, BookStatus status) throws DuplicateBookException{
            ensureNotDuplicate(book);
            super.save(book);
            addCopy(book, status);
            setEntitiesId(book);
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

    private void setEntitiesId(Book book){
        book.getAuthor().setId(authorNextId++);
        book.getCategory().setId(categoryNextId++);
    }
}
