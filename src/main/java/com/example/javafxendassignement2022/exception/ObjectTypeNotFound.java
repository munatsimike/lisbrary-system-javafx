package com.example.javafxendassignement2022.exception;

public class ObjectTypeNotFound extends Exception {
    @Override
    public String toString() {
        return "Unknown object type, list cannot be saved";
    }
}
