package com.hiiragi.library.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import com.hiiragi.library.model.Loan;

@Component
public class LoanModelAssembler implements RepresentationModelAssembler<Loan, EntityModel<Loan>>{
    
    @Override
    public EntityModel<Loan> toModel(Loan loan){
        return EntityModel.of(loan,
            linkTo(methodOn(LoanController.class).one(loan.getId())).withSelfRel(),
            linkTo(methodOn(LoanController.class).all()).withRel("loans")
        );
    }
    
}
