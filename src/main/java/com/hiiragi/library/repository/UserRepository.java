package com.hiiragi.library.repository;

import java.util.Objects;
import java.util.Optional;

import com.hiiragi.library.exceptions.DuplicateUserException;
import com.hiiragi.library.model.User;

public class UserRepository
        extends InMemoryRepository<User> {

    public boolean existsByEmail(String email) {
        return this.entities.stream()
                            .anyMatch((user) -> Objects.equals(user.getEmail(), email));
    }
    
    @Override
    public User save(User user){
        ensureNotDuplicate(user);    
        return super.save(user);
    }

    private void ensureNotDuplicate(User user) {
        if (findByLogin(user.getLogin()).isPresent()){
            throw new DuplicateUserException("Tried adding duplicate user with login: "+user.getLogin());
        }            
        if (findByEmail(user.getEmail()).isPresent()){
            throw new DuplicateUserException("Tried adding duplicate user with email: "+user.getEmail());
        }    
    }

    public Optional<User> findByEmail(String email){
        return this.entities.stream()
                            .filter((user) -> Objects.equals(user.getEmail(), email))
                            .findAny();
    }

    public Optional<User> findByName(String name){
        return this.entities.stream()
                            .filter((user) -> Objects.equals(user.getName(), name))
                            .findAny();
    }

    public Optional<User> findByLogin(String login){
        return this.entities.stream()
                            .filter((user) -> Objects.equals(user.getLogin(), login))
                            .findAny();
    }

}