package com.example.javafxendassignement2022.exception;

public class MemberNotFoundException extends Exception{
    public String toString(){
        return ("Member not found, verify member identifier code");
    }
}
