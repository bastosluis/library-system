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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hiiragi.library.exceptions.LoanNotFoundException;
import com.hiiragi.library.model.Loan;
import com.hiiragi.library.service.LibraryService;
import com.hiiragi.library.service.LoanService;

@RestController
@RequestMapping("/loans")
public class LoanController {
    
    private final LoanService loanService;
    private final LibraryService libraryService;
    private final LoanModelAssembler assembler;
    
    public LoanController(LoanService loanService, LibraryService libraryService, LoanModelAssembler assembler){
        this.loanService = loanService;
        this.libraryService = libraryService;
        this.assembler = assembler;
    }

    @GetMapping
    public CollectionModel<EntityModel<Loan>> all() {
        List<EntityModel<Loan>> loans = loanService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(loans, linkTo(methodOn(LoanController.class).all()).withSelfRel());
    }
    
    @GetMapping("/{id}")
    public EntityModel<Loan> one(@PathVariable Long id) {
        Loan loan = loanService.findById(id).orElseThrow(() -> new LoanNotFoundException(id));
        return assembler.toModel(loan);
    }    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteById(@PathVariable Long id){
        loanService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/borrow")
    public ResponseEntity<EntityModel<Loan>> borrow(
            @RequestParam Long bookId,
            @PathVariable Long userId) {

        Loan loan = libraryService.borrowBook(bookId, userId);
        EntityModel<Loan> entityModel = assembler.toModel(loan);

        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }
}
