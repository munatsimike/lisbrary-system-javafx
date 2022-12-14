package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ObjectTypeNotFound;
import com.example.javafxendassignement2022.exception.ItemNotFoundException;
import com.example.javafxendassignement2022.exception.MemberNotFoundException;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.model.Transaction;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.util.List;

// database object contains and stores items , members and transactions
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
        members.add(new Member(1, "Michael", "Tim", LocalDate.of(2022, 10, 18)));
        members.add(new Member(2, "John", "Thom", LocalDate.of(2022, 10, 17)));
        members.add(new Member(3, "Peter", "Jon", LocalDate.of(2022, 10, 17)));
        members.add(new Member(4, "Simon", "Joe", LocalDate.of(2022, 10, 19)));
        members.add(new Member(5, "Michael", "RukudzoM7", LocalDate.of(2022, 10, 17)));
    }

    // add init items into the items array
    private void iniItemDatabase() {
        items = FXCollections.observableArrayList();
        items.add(new Item(1, Availability.NO, "Lord of the ring", "Tinashe"));
        items.add(new Item(2, Availability.YES, "The femine", "Student"));
        items.add(new Item(3, Availability.NO, "Monarch", "Tariro"));
        items.add(new Item(4, Availability.NO, "The Island", "Student"));
        items.add(new Item(5, Availability.YES, "Treasure Island", "Tatenda"));
    }

    // return items, members  or transactions
    @SuppressWarnings("unchecked")
    public <T> ObservableList<T> getRecords(Class<T> tClass) {
        if (tClass.equals(Item.class)) {
            return (ObservableList<T>) items;
        } else if (tClass.equals(Member.class)) {
            return (ObservableList<T>) members;
        }
        return (ObservableList<T>) transactions;
    }

    // delete record
    public <T> void deleteRecord(int id, Class<T> tClass) {
        if (tClass.equals(Item.class)) {
            items.removeIf(item -> item.getItemCode() == id);
        } else {
            members.removeIf(member -> member.getIdentifier() == id);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> void saveListToDatabase(List<T> list) throws ObjectTypeNotFound {
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
            throw new ObjectTypeNotFound();
        }
    }

    public <T> void addRecord(T object) {
        if (object instanceof Item item) {
            items.add(item);
        } else if (object instanceof Member member) {
            members.add(member);
        } else if (object instanceof Transaction transaction) {
            transactions.add(transaction);
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

    public void isItemCodeValid(int code) throws ItemNotFoundException {
        for (Item item : items) {
            if (item.getItemCode() == code) {
                return;
            }
        }
        throw new ItemNotFoundException();
    }

    public void isMemberIdValid(int id) throws MemberNotFoundException {
        for (Member member : members) {
            if (member.getIdentifier() == id) {
                return;
            }
        }
        throw new MemberNotFoundException();
    }

    public Item getItem(int code) throws ItemNotFoundException {
        return items.stream().filter(item -> item.getItemCode() == code)
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
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

    public Transaction getTransaction(int itemId) throws ItemNotFoundException {
        return transactions.stream().filter(item -> item.getItemCode() == itemId)
                .findFirst()
                .orElseThrow(ItemNotFoundException::new);
    }

    public int getTransactionId() {
        int id = transactions.size();
        return ++id;
    }

    public int getMemberIdentifier() {
        int id = members.size();
        return ++id;
    }

    public int getItemCode() {
        int id = items.size();
        return ++id;
    }

    public void clearTransaction(int itemCode) {
        for (Transaction transaction : transactions) {
            if (transaction.getItemCode() == itemCode && transaction.getTransactionType() == TransactionType.LEND) {
                transactions.remove(transaction);
                return;
            }
        }
    }
}
