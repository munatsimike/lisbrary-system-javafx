package com.example.javafxendassignement2022.enums;

public enum Availability {
    YES, NO;

    @Override
    public String toString() {
        return switch (this) {
            case YES -> "Yes";
            case NO -> "No";
        };
    }
}
