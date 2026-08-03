package com.hiiragi.library.model;

import java.time.LocalDate;

import com.hiiragi.library.enums.LoanStatus;

public class Loan extends BaseEntity{
    private Long bookId;
    private Long bookCopyId;
    private Long userId;
    private LoanStatus status;
    private LocalDate loanDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
    
    public Loan(Long bookId, Long bookCopyId, Long userId, LoanStatus status, LocalDate loanDate, LocalDate dueDate, LocalDate returnDate) {
        this.bookId = bookId;
        this.bookCopyId = bookCopyId;
        this.userId = userId;
        this.status = status;
        this.loanDate = loanDate;
        this.dueDate = dueDate;
        this.returnDate = returnDate;
    }
    
    public Loan(Long bookId, Long bookCopyId, Long userId, LocalDate dueDate){
        this(bookId, bookCopyId, userId, LoanStatus.ACTIVE, LocalDate.now(), dueDate, null);    
    }
    
    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public Long getBookCopyId() {
        return bookCopyId;
    }
    
    public void setBookCopyId(Long bookCopyId) {
        this.bookCopyId = bookCopyId;
    }
    
    public Long getUserId() {
        return userId;
    }
    
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    
    public LocalDate getLoanDate() {
        return loanDate;
    }

    public void setLoanDate(LocalDate loanDate) {
        this.loanDate = loanDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public LoanStatus getStatus() {
        return status;
    }

    public void setStatus(LoanStatus status) {
        this.status = status;
    }

    public void markAsReturned(LocalDate returnDate) {
        this.status = LoanStatus.RETURNED;
        this.returnDate = returnDate;
    }
}
