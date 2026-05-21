package com.hiiragi.library.repository;

import java.util.ArrayList;
import java.util.List;

import com.hiiragi.library.model.User;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    Long nextId = 1L;

    public User save(User user){
        user.setId(nextId);
        nextId++;
        users.add(user);
        return user;
    }
    public List<User> findAll(){
        List<User> returnList = users;
        return returnList;
    }
    public User findById(Long id){
        for(User user : users){
            if(user.getId() != null && user.getId().equals(id)){
                return user;
            }
        }
        return null;
    }
    public boolean deleteById(Long id){
        for(User user : users){
            if(user.getId() != null && user.getId().equals(id)){
                users.remove(user);
                return true;
            }
        }
        return false;
    }
    public boolean existsByEmail(String email){
        for (User user : users){
            if(email != null && email.equals(user.getEmail())){
                return true;                
            }
        }
        return false;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(User user : users){
            sb.append(user.toString()).append("\n");
        }
        return sb.toString();
    }
}
