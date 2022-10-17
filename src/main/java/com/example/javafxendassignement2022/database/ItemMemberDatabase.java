package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ItemNotFoundException;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

public class ItemMemberDatabase {
    ObservableList<Item> items;
    ObservableList<Member> members;
    ObservableList<Transaction> transactions;

    public ItemMemberDatabase() {
        iniItemDatabase();
        initMemberDatabase();
        transactions();
    }

    private void transactions() {
        transactions = FXCollections.observableArrayList();
        transactions.add(new Transaction(1, 1, LocalDate.of(2022, 9, 1), TransactionType.LEND));
    }

    private void initMemberDatabase() {
        members = FXCollections.observableArrayList();
        members.add(new Member(1, "Michael", "RukudzoM7", LocalDate.of(2022, 10, 18)));
        members.add(new Member(2, "John", "RukudzoM7", LocalDate.of(2022, 10, 17)));
        members.add(new Member(3, "Peter", "RukudzoM7", LocalDate.of(2022, 10, 17)));
        members.add(new Member(4, "Simon", "RukudzoM7", LocalDate.of(2022, 10, 19)));
        members.add(new Member(5, "Michael", "RukudzoM7", LocalDate.of(2022, 10, 17)));
    }

    private void iniItemDatabase() {
        items = FXCollections.observableArrayList();
        items.add(new Item(1, Availability.No, "12345678", "Tinashe"));
        items.add(new Item(2, Availability.Yes, "RukudzoM7", "Student"));
        items.add(new Item(3, Availability.No, "12345678", "Tariro"));
        items.add(new Item(4, Availability.No, "12345678", "Student"));
        items.add(new Item(5, Availability.Yes, "12345678", "Tatenda"));
    }

    public <T> ObservableList<T> getRecords(Class<T> tClass) {
        if (tClass.equals(Item.class)) {
            return (ObservableList<T>) items;
        } else if (tClass.equals(Member.class)) {
            return (ObservableList<T>) members;
        }
        return (ObservableList<T>) transactions;
    }

    public <T> void deleteRecord(int id, Class<T> tClass) {
        if (tClass.equals(Item.class)) {
            items.removeIf(item -> item.getItemCode() == id);
        } else {
            members.removeIf(member -> member.getIdentifier() == id);
        }
    }

    public <T> void saveListToDatabase(List<T> list) throws Exception {
        if (list.get(0) instanceof Item) {
            items.clear();
            items.addAll((List<Item>) list);
        } else if (list.get(0) instanceof Member) {
            members.clear();
            members.addAll((List<Member>) list);
        } else if (list.get(0) instanceof Transaction) {
            transactions.clear();
            transactions.addAll((List<Transaction>) list);
        } else {
            throw new Exception("Unknown object type, list cannot be saved");
        }
    }

    public <T> void addRecord(T object) {
        if (object instanceof Item) {
            items.add((Item) object);
        } else if (object instanceof Member) {
            members.add((Member) object);
        } else if (object instanceof Transaction) {
            transactions.add((Transaction) object);
        }
    }

    public void editItem(Item newItem) {
        for (Item oldItem : items) {
            if (oldItem.getItemCode() == newItem.getItemCode()) {
                oldItem.setAvailable(newItem.getAvailable());
                if (newItem.getTitle() != null) {
                    oldItem.setTitle(newItem.getTitle());
                    oldItem.setAuthor(newItem.getAuthor());
                }
            }
        }
    }

    public Item getItem(int code) throws Exception {
        return items.stream().filter(item -> item.getItemCode() == code)
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
    }

    /**********************************/

    public void editMember(Member newMember) {
        for (Member oldMember : members) {
            if (oldMember.getIdentifier() == newMember.getIdentifier()) {
                oldMember.setFirstName(newMember.getFirstName());
                oldMember.setLastName(newMember.getLastName());
                oldMember.setDateOfBirth(newMember.getDateOfBirth());
            }
        }
    }

    /************************************************************/

    public Transaction getTransaction(int code) throws Exception {
        return transactions.stream().filter(item -> item.getItemCode() == code)
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
    }

    public int getTransactionId() {
        int id = transactions.size() + 1;
        return id++;
    }

    public int getMemberIdentifier() {
        int id = members.size() + 1;
        return id++;
    }

    public int getItemCode() {
        int id = items.size() + 1;
        return id++;
    }

}
