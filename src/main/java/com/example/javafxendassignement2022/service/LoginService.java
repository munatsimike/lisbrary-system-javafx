package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.UserDatabase;
import com.example.javafxendassignement2022.model.User;

public record LoginService(UserDatabase userDatabase) {

    public User getUser(User user) throws Exception {
        validateUserName(user.getUsername());
        validatePassword(user.getPassword());
        return userDatabase.getUser(user);
    }

    private void validatePassword(String password) throws Exception {
        if (!password.matches(".*[A-Z].*")) {
            throw new Exception("password must contain at least 1 uppercase letter");
        } else if (!password.matches(".*[a-z].*")) {
            throw new Exception("password must contain at least 1 lowercase letter");
        } else if (!password.matches(".*[@#*\\$%^&+=].*")) {
            throw new Exception("password must contain at least 1 special character: [@#*$&+=]");
        }
    }

    private void validateUserName(String username) throws Exception {
        if (!username.matches("^[0-9a-zA-Z]+$")) {
            throw new Exception("Username must contain letters and numbers only");
        }
    }
}
