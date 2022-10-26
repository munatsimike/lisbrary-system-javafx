package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.exception.ItemNotFoundException;
import com.example.javafxendassignement2022.model.Item;
import javafx.collections.ObservableList;

public record ItemService(ItemMemberDatabase database) {

    public void editItem(Item item) {
        database.editItem(item);
    }

    public void addItem(Item item) {
        database.addRecord(item);
    }

    public Item getItem(int itemId) throws ItemNotFoundException {
        return database.getItem(itemId);
    }

    public ObservableList<Item> getItems(Class<Item> itemClass) {
        return database.getRecords(itemClass);
    }

    public void deleteItem(int itemId, Class<Item> itemClass) {
        database.deleteRecord(itemId, itemClass);
    }

    public int getItemCode() {
        return database.getItemCode();
    }

    public void isItemIdValid(int code) throws ItemNotFoundException {
        database.isItemCodeValid(code);
    }
}
