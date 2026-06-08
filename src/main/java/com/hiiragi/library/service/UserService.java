package com.hiiragi.library.service;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.UserRepository;

public class UserService extends BaseService<User, UserRepository>{
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "\\d{8,15}";

    public UserService(UserRepository userRepository){
        super(userRepository);
    }

    public User createUser(String name, String email, String phone, UserRole role){
        if (!isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email");
        }
        if (!isValidPhone(phone)){
            throw new IllegalArgumentException("Invalid phone");
        }
        if(repository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(name, email, phone, true, 3, role);

        return repository.save(user);
    }

    public boolean deactivate(Long id){
        User user = repository.findById(id);
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
