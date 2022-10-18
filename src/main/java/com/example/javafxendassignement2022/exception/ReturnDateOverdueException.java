package com.example.javafxendassignement2022.exception;

public class ReturnDateOverdueException extends  Exception{
    int daysOverDue;
    public ReturnDateOverdueException(int numOfDays){
        this.daysOverDue = numOfDays;
    }
    public String toString(){
        return "Item received successfully, but "+ daysOverDue + " days late";
    }
}
