package com.example.javafxendassignement2022.service;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ItemNotFoundException;
import com.example.javafxendassignement2022.exception.MemberNotFoundException;
import com.example.javafxendassignement2022.exception.ReturnDateOverdueException;
import com.example.javafxendassignement2022.exception.TransactionError;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Transaction;

import java.time.Duration;
import java.time.LocalDate;

public record TransactionService(ItemMemberDatabase database, ItemService itemService, MemberService memberService) {

    // process lend transaction
    public void lend(int itemId, int memberIdentifier) throws TransactionError, ItemNotFoundException, MemberNotFoundException {
        processReturns(new Item(itemId, Availability.NO), TransactionType.LEND);
        itemService.isItemIdValid(itemId);
        memberService.isMemberIdValid(memberIdentifier);
        database.addRecord(new Transaction(itemId, memberIdentifier, LocalDate.now(), TransactionType.LEND));
    }

    // process receive transactions
    public void receive(int code) throws TransactionError, ItemNotFoundException, ReturnDateOverdueException {
        processReturns(new Item(code, Availability.YES), TransactionType.RECEIVE);
        database.addRecord(new Transaction(database.getTransactionId(), code, LocalDate.now(), TransactionType.RECEIVE));
        long duration = calculateDuration(code);
        if (duration > 21) {
            clearLendRecord(code);
            throw new ReturnDateOverdueException(Math.toIntExact(duration));
        }
        clearLendRecord(code);
    }

    private void clearLendRecord(int itemCode) {
        database.clearTransaction(itemCode);
    }

    private Long calculateDuration(int itemCode) throws ItemNotFoundException {
        LocalDate dateLent = database.getTransaction(itemCode).getDate();
        Duration duration = Duration.between(dateLent.atStartOfDay(), LocalDate.now().atStartOfDay());
        return duration.toDays();
    }

    private void processReturns(Item item, TransactionType type) throws TransactionError, ItemNotFoundException {
        Item item1 = itemService.getItem(item.getItemCode());
        if (item1.getAvailable() == item.getAvailable()) {
            if (type == TransactionType.RECEIVE) {
                throw new TransactionError("Duplicate transaction, item already received");
            } else {
                throw new TransactionError("Item not available, lend another item");
            }
        }
        itemService.editItem(item);
    }
}