package com.hiiragi.library.model;

class BookCopy {
    private int id;
    private Book bookId;
    // private BookStatus status;
    
    public BookCopy(int id, Book bookId) {
        this.id = id;
        this.bookId = bookId;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }
}
