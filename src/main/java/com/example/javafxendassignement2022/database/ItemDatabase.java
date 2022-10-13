package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.model.Item;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class ItemDatabase {
    ObservableList<Item> items;

    public ItemDatabase() {
        items = FXCollections.observableArrayList();
        items.add(new Item(1, "No", "12345678", "Tinashe"));
        items.add(new Item(2, "Yes", "RukudzoM7", "Student"));
        items.add(new Item(3, "No", "12345678", "Tariro"));
        items.add(new Item(4, "Yes", "12345678", "Student"));
        items.add(new Item(5, "No", "12345678", "Tatenda"));
    }

    public int getId() {
        int id = items.size() + 1;
        return id++;
    }

    public ObservableList<Item> getItems() {
        return items;
    }

    public void deleteItem(int id) {
        items.removeIf(item -> item.getItemCode() == id);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void editItem(Item newItem) {
        for (Item oldItem : items) {
            if (oldItem.getItemCode() == newItem.getItemCode()) {
                oldItem.setTitle(newItem.getTitle());
                oldItem.setAuthor(newItem.getAuthor());
                oldItem.setAvailable(newItem.getAvailable());
            }
        }
    }
}
