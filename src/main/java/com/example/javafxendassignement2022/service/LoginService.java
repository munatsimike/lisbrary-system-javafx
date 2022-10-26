package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.UserDatabase;
import com.example.javafxendassignement2022.exception.InvalidInput;
import com.example.javafxendassignement2022.exception.InvalidPassword;
import com.example.javafxendassignement2022.exception.WrongUsernamePasswordCombination;
import com.example.javafxendassignement2022.model.User;

public record LoginService(UserDatabase userDatabase) {

    public User getUser(User user) throws WrongUsernamePasswordCombination, InvalidInput, InvalidPassword {
        validateUserName(user.getUsername());
        validatePassword(user.getPassword());
        return userDatabase.getUser(user);
    }

    private void validatePassword(String password) throws InvalidPassword {
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPassword("password must contain at least 1 uppercase letter");
        } else if (!password.matches(".*[a-z].*")) {
            throw new InvalidPassword("password must contain at least 1 lowercase letter");
        } else if (!password.matches(".*[@#*\\$%^&+=].*")) {
            throw new InvalidPassword("password must contain at least 1 special character: [@#*$&+=]");
        }
    }

    private void validateUserName(String username) throws InvalidInput {
        if (!username.matches("^[0-9a-zA-Z]+$")) {
            throw new InvalidInput("Username must contain letters and numbers only");
        }
    }
}
