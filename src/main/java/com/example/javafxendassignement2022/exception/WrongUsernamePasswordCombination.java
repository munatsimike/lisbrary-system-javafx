package com.example.javafxendassignement2022.exception;

public class WrongUsernamePasswordCombination extends Throwable {
    @Override
    public String toString() {
        return "Username and password combination not found";
    }
}
