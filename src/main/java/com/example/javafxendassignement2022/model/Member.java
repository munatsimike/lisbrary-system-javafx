package com.example.javafxendassignement2022.model;

import java.time.LocalDate;

public class Member extends Person {
    private final int identifier;
    private LocalDate dateOfBirth;

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth) {
        super(firstName, lastName);
        this.identifier = identifier;
        this.dateOfBirth = dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdentifier() {
        return identifier;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }
}
