package com.hiiragi.library.model;

// import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;

import com.hiiragi.library.enums.BookStatus;

class Book {
    private int id;
    private String title;
    private String isbn;
    private String description;
    private Year publicationYear;
    // private Author author;
    // private Category category;
    private ArrayList<BookCopy> copies;
    
    public Book(int id, String title, String isbn, String description, Year publicationYear, ArrayList<BookCopy> copies) {
        this.id = id;
        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publicationYear = publicationYear;
        this.copies = copies;
    }

    public Book(int id, String title, String isbn, String description, Year publicationYear){
        this(id, title, isbn, description, publicationYear, new ArrayList<BookCopy>());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Year getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(Year publicationYear) {
        this.publicationYear = publicationYear;
    }

    public ArrayList<BookCopy> getCopies() {
        return copies;
    }

    public void setCopies(ArrayList<BookCopy> copies) {
        this.copies = copies;
    }

    public void addCopy(BookCopy copy){
        this.copies.add(copy);
    }
    
    public void removeCopy(BookCopy copy){
        this.copies.remove(copy);
    }

    public BookCopy getAvailableCopy() {
        if (copies == null) {
            return null;
        }

        return copies.stream()
                    .filter(c -> c.getStatus() == BookStatus.AVAILABLE)
                    .findFirst()
                    .orElse(null);
    }
}
