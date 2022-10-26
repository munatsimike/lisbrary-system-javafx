package com.example.javafxendassignement2022.exception;

public abstract class BaseException extends Exception {
    protected final String message;

    protected BaseException(String msg) {
        this.message = msg;
    }

    @Override
    public String toString() {
        return message;
    }
}
