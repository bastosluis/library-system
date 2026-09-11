package com.hiiragi.library.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiiragi.library.exceptions.BookNotFoundException;
import com.hiiragi.library.model.Book;
import com.hiiragi.library.service.BookService;

@RestController
@RequestMapping("/books")
public class BookController {
    
    private final BookService bookService;
    private final BookModelAssembler assembler;
    
    public BookController(BookService bookService, BookModelAssembler assembler){
        this.bookService = bookService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Book>> all() {
        List<EntityModel<Book>> books = bookService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(books, linkTo(methodOn(BookController.class).all()).withSelfRel());
    }
    
    @GetMapping("/{id}")
    public EntityModel<Book> one(@PathVariable Long id) {
        Book book = bookService.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        return assembler.toModel(book);
    }

    @GetMapping("/search")
    public EntityModel<Book> getByTitle(@RequestParam String title){
        Book book = bookService.findByTitle(title).orElseThrow(() -> new BookNotFoundException(title));
        return assembler.toModel(book); 
    }
    
    @PostMapping
    public ResponseEntity<?> create(@RequestBody Book book){
        EntityModel<Book> entityModel = assembler.toModel(bookService.add(book)); 
        return ResponseEntity
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody Book newBook){
        Book updatedBook = bookService.update(newBook, id);
        EntityModel<Book> entityModel = assembler.toModel(updatedBook);
        return ResponseEntity.ok(entityModel); 
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteBookById(@PathVariable Long id){
        bookService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(params = "title")
    public ResponseEntity<?> deleteBookByTitle(@RequestParam String title){
        bookService.removeByTitle(title);
        return ResponseEntity.noContent().build();
    }

}
