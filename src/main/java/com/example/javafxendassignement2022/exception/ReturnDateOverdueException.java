package com.example.javafxendassignement2022.exception;

public class ReturnDateOverdueException extends Exception {
    private final int daysOverDue;

    public ReturnDateOverdueException(int numOfDays) {
        this.daysOverDue = numOfDays;
    }

    @Override
    public String toString() {
        return "Item received successfully, but " + daysOverDue + " days late";
    }
}
