package com.example.javafxendassignement2022.exception;

public class ItemNotFoundException  extends Exception{
    public String toString(){
        return ("Item not found, make sure you entered the code correctly");
    }
}
