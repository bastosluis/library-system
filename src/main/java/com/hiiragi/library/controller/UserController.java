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
import org.springframework.web.bind.annotation.RestController;

import com.hiiragi.library.exceptions.DuplicateUserException;
import com.hiiragi.library.exceptions.UserNotFoundException;
import com.hiiragi.library.model.User;
import com.hiiragi.library.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    private final UserModelAssembler assembler;

    public UserController(UserService userService, UserModelAssembler assembler) {
        this.userService = userService; 
        this.assembler = assembler;
    }
    
    @GetMapping
    public CollectionModel<EntityModel<User>> all(){
        List<EntityModel<User>> users = userService.findAll().stream()
            .map(assembler::toModel)
            .collect(Collectors.toList());
        return CollectionModel.of(users, linkTo(methodOn(UserController.class).all()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<User> one(@PathVariable Long id){
        User user = userService.findById(id).orElseThrow(() -> new UserNotFoundException(id));
        return assembler.toModel(user);
    }
    
    @PostMapping
    public ResponseEntity<?> newUser(@RequestBody User user){
        EntityModel<User> entityModel = assembler.toModel(userService.add(user)
                                            .orElseThrow(() -> new DuplicateUserException("User "+user.getLogin()+" could not be added.")));
        
        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
            .body(entityModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        User updatedUser = userService.update(user, id);
        EntityModel<User> entityModel = assembler.toModel(updatedUser);
        return ResponseEntity //
            .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()) //
            .body(entityModel);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        userService.removeById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{title}")
    public ResponseEntity<?> deleteUserByLogin(@PathVariable String login){
        userService.removeByLogin(login);
        return ResponseEntity.noContent().build();
    }

}
