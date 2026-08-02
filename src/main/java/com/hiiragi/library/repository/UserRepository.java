package com.hiiragi.library.repository;

import java.util.Optional;

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