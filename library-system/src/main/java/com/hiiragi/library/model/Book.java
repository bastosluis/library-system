package com.hiiragi.library.model;

// import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;

public class Book {
    private Long id = null;
    private String title;
    private String isbn;
    private String description;
    private Year publicationYear;
    private Author author;
    private Category category;
    private ArrayList<BookCopy> copies;

    public Book(String title,
                String isbn,
                String description,
                Year publicationYear,
                Author author,
                Category category,
                ArrayList<BookCopy> copies) {

        this.title = title;
        this.isbn = isbn;
        this.description = description;
        this.publicationYear = publicationYear;
        this.author = author;
        this.category = category;
        this.copies = copies;
    }

    @Override
    public String toString(){
        // id might be null
        return String.format("Title: %s%nId: %d%nISBN: %s%nDescription: %s%nYear of publication: %s%nAuthor: %s%nCategory: %s%nCopies: %d", 
                            title, 
                            id, 
                            isbn, 
                            description, 
                            publicationYear, 
                            author.getName(), 
                            category.getName(), 
                            copies.size());
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
    
    public Author getAuthor() {
        return author;
    }
    
    public void setAuthor(Author author) {
        this.author = author;
    }
    
    public Category getCategory() {
        return category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
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
    
}
