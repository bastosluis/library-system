package com.hiiragi.library.model;

import com.hiiragi.library.enums.BookStatus;

public class BookCopy {
    private int id;
    private int bookId;
    private BookStatus status;

    public BookCopy(int id, int bookId, BookStatus status) {
        this.id = id;
        this.bookId = bookId;
        this.status = status;
    }

    public BookCopy(int id, int bookId){
        this(id, bookId, BookStatus.AVAILABLE);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
  
}