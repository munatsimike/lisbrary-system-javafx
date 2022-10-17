package com.example.javafxendassignement2022.model;
import com.example.javafxendassignement2022.enums.TransactionType;

import java.io.Serializable;
import java.time.LocalDate;

public class Transaction implements Serializable {
    int transactionId;
    int ItemCode;
    int memberIdentifier;
    LocalDate date;
    TransactionType transactionType;

    public int getTransactionId() {
        return transactionId;
    }

    public int getItemCode() {
        return ItemCode;
    }

    public int getMemberIdentifier() {
        return memberIdentifier;
    }

    public LocalDate getDate() {
        return date;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public Transaction(int itemCode, LocalDate date, TransactionType transactionType) {
        ItemCode = itemCode;
        this.date = date;
        this.transactionType = transactionType;
    }

    public Transaction(int itemCode, int memberIdentifier, LocalDate date, TransactionType transactionType) {
        this(itemCode, date, transactionType);
        this.memberIdentifier = memberIdentifier;
    }
}
