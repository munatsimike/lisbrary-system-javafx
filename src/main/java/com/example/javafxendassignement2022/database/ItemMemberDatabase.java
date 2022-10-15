package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class ItemMemberDatabase {
    ObservableList<Item> items;
    ObservableList<Member> members;

    public ItemMemberDatabase() {
        iniItemDatabse();
        initMemberDatabase();
    }

    private void iniItemDatabse(){
        items = FXCollections.observableArrayList();
        items.add(new Item(1, "No", "12345678", "Tinashe"));
        items.add(new Item(2, "Yes", "RukudzoM7", "Student"));
        items.add(new Item(3, "No", "12345678", "Tariro"));
        items.add(new Item(4, "Yes", "12345678", "Student"));
        items.add(new Item(5, "No", "12345678", "Tatenda"));
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
                oldItem.setTitle(newItem.getTitle());
                oldItem.setAuthor(newItem.getAuthor());
                oldItem.setAvailable(newItem.getAvailable());
            }
        }
    }

    /**********************************/

    private void initMemberDatabase() {
        members = FXCollections.observableArrayList();
        members.add(new Member(1, "Michael", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(2, "John", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(3, "Peter", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(4, "Simon", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(5, "Michael", "RukudzoM7", LocalDate.of(2022,10,17)));
    }

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
}
