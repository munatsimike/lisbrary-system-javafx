package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ItemOverdueException;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Transaction;

import java.time.Duration;
import java.time.LocalDate;

public record TransactionService(ItemMemberDatabase database) {

    public void lend(int code, int memberIdentifier) throws Exception {
        updateAvailability(new Item(code, Availability.No), TransactionType.LEND);
        database.addTransaction(new Transaction(code, memberIdentifier, LocalDate.now(), TransactionType.LEND));
    }

    public void receive(int code) throws Exception {
        updateAvailability(new Item(code, Availability.Yes), TransactionType.RECEIVE);
        database.addTransaction(new Transaction(database.getTransactionId(), code, LocalDate.now(), TransactionType.RECEIVE));
        if (isOverDue(code)) {
            throw new ItemOverdueException();
        }
    }

    private Boolean isOverDue(int itemCode) throws Exception {
        LocalDate dateLent = database.getTransaction(itemCode).getDate();
        Duration duration = Duration.between(dateLent.atStartOfDay(), LocalDate.now().atStartOfDay());
        return (duration.toDays() > 21);
    }

    private void updateAvailability(Item item, TransactionType type) throws Exception {
        Item item1 = database.getItem(item.getItemCode());
        if (item1.getAvailable() == item.getAvailable()) {
            if (type == TransactionType.RECEIVE) {
                throw new Exception("Duplicate transaction, item already received");
            } else {
                throw new Exception("Item not available, lend another item");
            }
        }
        database.editItem(item);
    }
}