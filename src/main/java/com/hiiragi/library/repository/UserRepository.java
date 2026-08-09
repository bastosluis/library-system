package com.hiiragi.library.repository;

import java.util.Optional;

import com.hiiragi.library.exceptions.DuplicateUserException;
import com.hiiragi.library.model.User;

public class UserRepository
        extends InMemoryRepository<User> {

    public boolean existsByEmail(String email) {
        for (User user : entities) {
            if (email.equals(user.getEmail())) {
                return true;
            }
        }
        return false;
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
        for (User user : entities) {
            if (user.getEmail().equals(email)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByName(String name){
        for (User user : entities) {
            if (user.getName().equals(name)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> findByLogin(String login){
        for (User user : entities) {
            if (user.getLogin().equals(login)){
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

}