package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ReturnDateOverdueException;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Transaction;

import java.time.Duration;
import java.time.LocalDate;

public record TransactionService(ItemMemberDatabase database) {

    public void lend(int code, int memberIdentifier) throws Exception {
        updateAvailability(new Item(code, Availability.NO), TransactionType.LEND);
        database.getItem(code);
        database.getMember(memberIdentifier);
        database.addRecord(new Transaction(code, memberIdentifier, LocalDate.now(), TransactionType.LEND));
    }

    public void receive(int code) throws Exception {
        updateAvailability(new Item(code, Availability.YES), TransactionType.RECEIVE);
        clearLandingDate(code);
        database.addRecord(new Transaction(database.getTransactionId(), code, LocalDate.now(), TransactionType.RECEIVE));
        long duration = calculateDuration(code);
        if (duration > 21) {
            throw new ReturnDateOverdueException(Math.toIntExact(duration));
        }

    }

    private void clearLandingDate(int itemCode){
        database.clearTransaction(itemCode);
    }

    private Long calculateDuration(int itemCode) throws Exception {
        LocalDate dateLent = database.getTransaction(itemCode).getDate();
        Duration duration = Duration.between(dateLent.atStartOfDay(), LocalDate.now().atStartOfDay());
        return duration.toDays();
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