package com.hiiragi.library.model;

// import java.lang.reflect.Array;
import java.time.Year;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class Book extends BaseEntity {
    private String title;
    private String isbn;
    private String description;
    private Year publicationYear;

    @ManyToOne
    private Author author;

    @ManyToOne
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

    @JsonCreator
    public Book(
        @JsonProperty("title") String title,
        @JsonProperty("isbn") String isbn,
        @JsonProperty("description") String description,
        @JsonProperty("year") Year publicationYear,
        @JsonProperty("author") Author author,
        @JsonProperty("category") Category category){
                
        this(title, isbn, description, publicationYear, author, category, new ArrayList<>());
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

    public Optional<BookCopy> getCopy(Long bookCopyId){
        return copies.stream()
            .filter(copy -> Objects.equals(bookCopyId, copy.getId()))
            .findFirst();
        // imperative version for comparison
        // if (copies.isEmpty()) return Optional.empty();
        // for (BookCopy copy : this.copies) {
        //     if (copy.getId().equals(copy)){
        //         return Optional.of(copy);
        //     }
        // }
        // return Optional.empty();
    }

    public boolean hasCopies() {
        return !this.copies.isEmpty();
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
