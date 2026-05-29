package com.hiiragi.library.repository;

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
    
    public User findByName(String name){
        for (User user : entities) {
            if (user.getName().equals(name)){
                return user;
            }
        }
        return null;
    }
    // @Override
    // public String toString() {
    //     StringBuilder sb = new StringBuilder();
    //     for(User user : entities){
    //         sb.append(user.toString()).append("\n");
    //     }
    //     return sb.toString();
    // }
}