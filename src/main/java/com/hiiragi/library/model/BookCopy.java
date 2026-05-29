package com.hiiragi.library.model;

import com.hiiragi.library.enums.BookStatus;

public class BookCopy extends BaseEntity{
    private Long bookId;
    private BookStatus status;

    public BookCopy(Long bookId, BookStatus status) {
        this.bookId = bookId;
        this.status = status;
    }

    public BookCopy(Long bookId){
        this(bookId, BookStatus.AVAILABLE);
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public BookStatus getStatus() {
        return status;
    }

    public void setStatus(BookStatus status) {
        this.status = status;
    }
  
}