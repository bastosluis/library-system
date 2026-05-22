package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.hiiragi.library.model.Book;
/**
 * BookRepository is a class to handle data storage regarding books. This class will implement database integration and such.
 * Since this class is surprisingly similar to UserRepository, it is to be noted that using a different approach to both classes utilizing 
 * class heritance and interface implementations should be preffered and will be a subject of future refactoring of this code.
 */
public class BookRepository {
    private List<Book> books = new ArrayList<>();
    Long nextId = 1L;
    
    public Book save(Book book){
        book.setId(nextId);
        nextId++;
        books.add(book);
        return book;
    }
    
    public List<Book> findAll(){
        return List.copyOf(books);
    }
    
    public Book findByTitle(String title) {
    return books.stream()
            .filter(book -> book.getTitle().equals(title))
            .findFirst();
}

}
