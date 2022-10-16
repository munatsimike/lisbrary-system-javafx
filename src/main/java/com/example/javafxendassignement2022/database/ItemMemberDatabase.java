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
import java.util.ArrayList;
import java.util.List;

public class ItemMemberDatabase {
    ObservableList<Item> items;
    ObservableList<Member> members;
    List<Transaction> transactions;

    public ItemMemberDatabase() {
        iniItemDatabase();
        initMemberDatabase();
        transactions();
    }

    private void transactions() {
        transactions = new ArrayList<>();
        transactions.add(new Transaction(1, 1, LocalDate.of(2022, 9, 1), TransactionType.LEND));
    }

    private void initMemberDatabase() {
        members = FXCollections.observableArrayList();
        members.add(new Member(1, "Michael", "RukudzoM7", LocalDate.of(2022, 10, 17)));
        members.add(new Member(2, "John", "RukudzoM7", LocalDate.of(2022, 10, 17)));
        members.add(new Member(3, "Peter", "RukudzoM7", LocalDate.of(2022, 10, 17)));
        members.add(new Member(4, "Simon", "RukudzoM7", LocalDate.of(2022, 10, 17)));
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

    public int getItemCode() {
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


    public int getMemberIdentifier() {
        int id = members.size() + 1;
        return id++;
    }

    public ObservableList<Member> getMembers() {
        return members;
    }

    public void deleteMember(int id) {
        members.removeIf(member -> member.getIdentifier() == id);
    }

    public void addMember(Member member) {
        members.add(member);
    }

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

    public List<Transaction> getAllTransactions() {
        return transactions;
    }

    public Transaction getTransaction(int code) throws Exception {
        return transactions.stream().filter(item -> item.getItemCode() == code)
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
    }

    public int getTransactionId() {
        int id = transactions.size() + 1;
        return id++;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
