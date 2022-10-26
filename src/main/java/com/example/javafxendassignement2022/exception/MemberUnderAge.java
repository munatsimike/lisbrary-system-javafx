package com.example.javafxendassignement2022.exception;

public class MemberUnderAge extends Throwable {

    @Override
    public String toString() {
        return "Members should be 12 years and above";
    }
}
