package com.example.javafxendassignement2022.model;

import java.io.Serializable;
import java.time.LocalDate;

public class Member implements Serializable {
    private final int identifier;
    protected String firstName;
    protected String lastName;
    private LocalDate dateOfBirth;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public int getIdentifier() {
        return identifier;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public Member(int identifier, String firstName, String lastName, LocalDate dateOfBirth) {
        this.identifier = identifier;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }
}
