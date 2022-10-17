package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.model.Transaction;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public record MainWindowService(ItemMemberDatabase database) {
    public MainWindowService(ItemMemberDatabase database) {
        this.database = database;
        populateDatabase();
    }

    private void populateDatabase() {
        try {
            List<Item> items = fetchListFromFile("items", Item.class);
            List<Member> members = fetchListFromFile("members", Member.class);
            List<Transaction> transactions = fetchListFromFile("transactions", Transaction.class);
            if (!items.isEmpty()) {
                database.saveListToDatabase(items);
            }

            if (!members.isEmpty()) {
                database.saveListToDatabase(members);
            }

            if (!transactions.isEmpty()) {
                database.saveListToDatabase(transactions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public <T> void saveListToFile(String filename, List<T> list) throws IOException {
        if (Files.exists(Path.of(filename + ".dat"))) {
            Files.delete(Path.of(filename + ".dat"));
        }

        try (FileOutputStream fos = new FileOutputStream(filename + ".dat");
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            for (T item : list) {
                oos.writeObject(item);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public <T> List<T> fetchListFromFile(String filename, Class<T> tClass) {
        List<T> items = new ArrayList<>();

        if (!Files.exists(Path.of(filename + ".dat"))) {
            return items;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename + ".dat"))) {
            while (true) {
                try {
                    T item = (T) ois.readObject();
                    if (tClass.isAssignableFrom(item.getClass())) {
                        items.add(item);
                    }
                } catch (EOFException e) {
                    break; // break out of the loop
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return items;
    }
}
