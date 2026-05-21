package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;

import com.hiiragi.library.model.Book;

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
}
