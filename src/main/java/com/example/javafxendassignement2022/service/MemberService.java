package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.exception.MemberNotFoundException;
import com.example.javafxendassignement2022.exception.MemberUnderAge;
import com.example.javafxendassignement2022.model.Member;
import javafx.collections.ObservableList;

import java.time.LocalDate;
import java.time.Period;

public record MemberService(ItemMemberDatabase database) {

    public int getMemberIdentifier() {
        return database.getMemberIdentifier();
    }

    public ObservableList<Member> getMembers(Class<Member> memberClass) {
        return database.getRecords(memberClass);
    }

    public void addMember(Member member) {
        database.addRecord(member);
    }

    public void editMember(Member member) {
        database.editMember(member);
    }

    public void deleteMember(int identifier, Class<Member> memberClass) {
        database.deleteRecord(identifier, memberClass);
    }

    public void isMemberIdValid(int id) throws MemberNotFoundException {
        database.isMemberIdValid(id);
    }

    public void calculateAge(LocalDate dateOfBirth) throws MemberUnderAge {
        Period period = dateOfBirth.until(LocalDate.now());
        if (period.getYears() < 13) {
            throw new MemberUnderAge();
        }
    }
}
