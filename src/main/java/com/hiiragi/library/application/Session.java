package com.hiiragi.library.application;

import java.util.Optional;

import com.hiiragi.library.model.User;

public class Session {

    private User currentUser;

    public void login(User user) {
        this.currentUser = user;
    }

    public void logout() {
        this.currentUser = null;
    }

    public Optional<User> getCurrentUser() {
        return Optional.of(currentUser);
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }
}