package com.example.javafxendassignement2022.model;

import java.time.LocalDate;
import java.util.Date;

public class Member {
    private int identifier;
    protected String firstName;
    protected String lastName;
    private LocalDate dateOfBirth;

    public void setIdentifier(int identifier) {
        this.identifier = identifier;
    }

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
