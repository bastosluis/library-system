package com.hiiragi.library.service;

import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.repository.BookRepository;

public class BookService extends BaseService<Book, BookRepository> {

    public BookService(BookRepository bookRepository){
        super(bookRepository);
    }
    
    public Book add(Book book, BookStatus status){
        Optional<Book> existentBook = this.repository.findByTitle(book.getTitle()); 
        if (existentBook.isPresent()){
            this.repository.addCopy(book, status);
            return book;
        }
        return this.repository.save(book, status);
    }

    public Book add(Book book){
        Optional<Book> existentBook = this.repository.findByTitle(book.getTitle()); 
        if (existentBook.isPresent()){
            this.repository.addCopy(book);
            return book;
        }
        return this.repository.save(book);
    }
    
    public Optional<Book> findByTitle(String title){
        return this.repository.findByTitle(title);
    }

    public Optional<BookCopy> getAvailableCopy(Book book){
        for (BookCopy copy : book.getCopies()) {
            if (copy.getStatus() == BookStatus.AVAILABLE) 
                return Optional.of(copy);
        }
        return Optional.empty();
    }
}