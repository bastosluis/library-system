package com.hiiragi.library.service;

import java.util.Optional;

import com.hiiragi.library.enums.UserRole;
import com.hiiragi.library.model.User;
import com.hiiragi.library.repository.UserRepository;

public class UserService extends BaseService<User, UserRepository>{
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "\\d{8,15}";

    public UserService(UserRepository userRepository){
        super(userRepository);
    }

    public Optional<User> add(User user){
        Optional<User> existentUser = this.repository.findByLogin(user.getLogin());
        if (existentUser.isPresent()){
            return Optional.empty();
        }
        return Optional.of(this.repository.save(user));
    }

    public User createUser(String name, String email, String phone, UserRole role, String login, String password){
        if (!isValidEmail(email)){
            throw new IllegalArgumentException("Invalid email");
        }
        if (!isValidPhone(phone)){
            throw new IllegalArgumentException("Invalid phone");
        }
        if(repository.existsByEmail(email)){
            throw new IllegalArgumentException("Email already exists");
        }

        User user = new User(name, email, phone, true, 3, role, login, password);

        return repository.save(user);
    }

    public Optional<User> login(String login, String password){
        Optional<User> optionalUser = repository.findByLogin(login);

        if (optionalUser.isEmpty()) {
            return Optional.empty();
        }

        User user = optionalUser.get();

        if (!user.getPassword().equals(password)) {
            return Optional.empty();
        }

        return optionalUser;
    }

    public boolean deactivate(Long id){
        Optional<User> user = repository.findById(id);
        if(user.isEmpty()){
            return false;
        }
        user.get().setActive(false);
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
