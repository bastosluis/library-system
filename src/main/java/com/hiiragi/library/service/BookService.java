package com.hiiragi.library.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.exceptions.NotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.repository.BookRepository;

@Service
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

    @Override
    public void removeById(Long id){
        try {
            super.removeById(id);
        } catch (NotFoundException e) {
            throw new BookNotFoundException(id);    
        }
    }

    public void removeByTitle(String title){
        Book book = findByTitle(title).orElseThrow(() -> new BookNotFoundException(title));
        this.removeById(book.getId());
    }

    public Book add(Book book){
        return add(book, BookStatus.AVAILABLE);
    }
    
    public Optional<Book> findByTitle(String title){
        return this.repository.findByTitle(title);
    }

    public Optional<BookCopy> getAvailableCopy(Book book){
        return book.getCopies().stream()
            .filter(copy -> copy.getStatus() == BookStatus.AVAILABLE)
            .findFirst();
    }
}