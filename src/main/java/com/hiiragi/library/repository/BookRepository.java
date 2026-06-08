package com.hiiragi.library.repository;

import com.hiiragi.library.model.Book;
import com.hiiragi.library.model.BookCopy;

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

    @Override
    public Book save(Book book){
        book.addCopy(new BookCopy(nextId));
        return super.save(book);
    }
}
