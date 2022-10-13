package com.example.javafxendassignement2022.database;

import com.example.javafxendassignement2022.model.Member;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.time.LocalDate;

public class MemberDatabase {
    ObservableList<Member> members;
    public MemberDatabase() {
        members = FXCollections.observableArrayList();
        members.add(new Member(1, "Michael", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(2, "John", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(3, "Peter", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(4, "Simon", "RukudzoM7", LocalDate.of(2022,10,17)));
        members.add(new Member(5, "Michael", "RukudzoM7", LocalDate.of(2022,10,17)));
    }

    public int getId() {
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
