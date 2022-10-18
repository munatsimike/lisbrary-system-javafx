package com.example.javafxendassignement2022.enums;

public enum Availability {
    Yes, No;

    @Override
    public String toString() {
        return switch (this) {
            case Yes -> "Yes";
            case No -> "No";
        };
    }
}
