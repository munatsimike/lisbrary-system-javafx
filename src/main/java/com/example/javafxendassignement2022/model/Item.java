package com.example.javafxendassignement2022.model;

import com.example.javafxendassignement2022.enums.Availability;

import java.io.Serializable;

public class Item implements Serializable {
    private final int itemCode;
    private Availability available;
    private String title;
    private String author;

    public int getItemCode() {
        return itemCode;
    }

    public Availability getAvailable() {
        return available;
    }

    public void setAvailable(Availability available) {
        this.available = available;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Item(int itemCode, Availability available) {
        this.itemCode = itemCode;
        this.available = available;
    }

    public Item(int itemCode, Availability availability, String title, String author) {
        this(itemCode, availability);
        this.title = title;
        this.author = author;
    }
}
