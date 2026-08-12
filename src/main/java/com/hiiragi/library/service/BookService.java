package com.hiiragi.library.service;

import java.util.Optional;

import com.hiiragi.library.enums.BookStatus;
import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;
import com.hiiragi.library.repository.BookRepository;

public class BookService extends BaseService<Book, BookRepository> {
    
    private AuthorizationService authorizationService;
    
    public BookService(BookRepository bookRepository, AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
        super(bookRepository);
    }

    public Book add(Book book, BookStatus status){
        authorizationService.requireRole(UserRole.ADMIN, UserRole.LIBRARIAN);

        Optional<Book> existentBook = this.repository.findByTitle(book.getTitle()); 
        if (existentBook.isPresent()){
            this.repository.addCopy(book, status);
            return book;
        }
        return this.repository.save(book, status);
    }

    @Override
    public void removeById(Long id){
        authorizationService.requireRole(UserRole.ADMIN, UserRole.LIBRARIAN);
        super.removeById(id);
    }

    public Book add(Book book){
        return add(book, BookStatus.AVAILABLE);
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
    
    public void setAuthorizationService(AuthorizationService authorizationService){
        this.authorizationService = authorizationService;
    }

    public AuthorizationService getAuthorizationService() {
        return authorizationService;
    }
}