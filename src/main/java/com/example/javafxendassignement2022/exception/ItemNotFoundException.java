package com.example.javafxendassignement2022.exception;

public class ItemNotFoundException extends Exception {
    @Override
    public String toString() {
        return ("Item not found, verify item code");
    }
}
