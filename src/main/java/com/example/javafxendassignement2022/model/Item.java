package com.example.javafxendassignement2022.model;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Item implements Serializable {
    private int itemCode;
    private String available;
    private String title;
    private String author;

    public int getItemCode() {
        return itemCode;
    }

    public void setItemCode(int itemCode) {
        this.itemCode = itemCode;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
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

    public Item(int itemCode, String availability, String title, String author) {
        this.itemCode = itemCode;
        this.available = availability;
        this.title = title;
        this.author = author;
    }

    public boolean isSamePerson(Item person1, Item person2) {
        for (Field field : getAllFields(person1.getClass())) {
            try {
                if (!field.get(person1).equals(field.get(person2)))
                    return false;
            } catch (IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    // get all fields of a child class and base classes
    public static List<Field> getAllFields(Class<?> type) {
        List<Field> fields = new ArrayList<>();
        for (Class<?> c = type; c != null; c = c.getSuperclass()) {
            fields.addAll(Arrays.asList(c.getDeclaredFields()));
        }
        return fields;
    }
}
