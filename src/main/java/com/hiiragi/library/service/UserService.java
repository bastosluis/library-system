package com.hiiragi.library.service;

import java.util.List;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "\\d{8,15}";

    public UserService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    public User createUser(String name, String email, String phone, UserRole role){
        if (!isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email");
        }
        if (!isValidPhone(phone)){
            throw new IllegalArgumentException("Invalid phone");
        }
        if(userRepository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(name, email, phone, true, 3, role);

        return userRepository.save(user);
    }

    public List<User> listUsers(){
        return userRepository.findAll();
    }

    public User findUserById(Long id){
        return userRepository.findById(id);
    }
    public boolean deleteUser(Long id){
        try {
            userRepository.deleteById(id);
            return true;
        } catch (Exception e) {
            System.err.println(e.getCause());    
            return false;
        }
    }
    public boolean deactivateUser(Long id){
        User user = userRepository.findById(id);
        if(user == null){
            return false;
        }
        user.setActive(false);
        return true;
    }


    // Validation Services
    private boolean isValidEmail(String email){
        return email != null && email.matches(EMAIL_REGEX);
    }
    private boolean isValidPhone(String phone){
        return phone != null && phone.matches(PHONE_REGEX);
    }
}
