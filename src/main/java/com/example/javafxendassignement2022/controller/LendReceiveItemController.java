package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ItemOverdueException;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.service.TransactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LendReceiveItemController implements Initializable {
    @FXML
    public TextField itemCodeLend;
    @FXML
    public TextField memberIdentifier;
    @FXML
    public TextField itemCodeReceive;
    @FXML
    public Button receiveItemBtn;
    @FXML
    public Button lendItemBtn;

    @FXML
    private NotificationController notificationController;
    private ItemTableController itemTableController;
    private final ItemMemberDatabase itemDatabase;
    private TransactionService transaction;

    public LendReceiveItemController(ItemMemberDatabase itemDataBase) {
        this.itemDatabase = itemDataBase;
        transaction = new TransactionService(itemDataBase);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        focusChangeListener();
    }

    public void onButtonClick(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                validateItemMemberId(itemCodeLend.getText(), TransactionType.LEND);
                validateItemMemberId(memberIdentifier.getText(), TransactionType.LEND);
                transaction.lend(Integer.parseInt(itemCodeLend.getText()), Integer.parseInt(memberIdentifier.getText()));
                notificationController.setNotificationText("Item lent successfully", NotificationType.Success);
            } else {
                validateItemMemberId(itemCodeReceive.getText(), TransactionType.RECEIVE);
                transaction.receive(Integer.parseInt(itemCodeReceive.getText()));
                notificationController.setNotificationText("Item successfully received", NotificationType.Success);
            }
        } catch (ItemOverdueException e) {
            notificationController.setNotificationText(e.toString(), NotificationType.Info);
        } catch (Exception e) {
            notificationController.setNotificationText(e.getMessage(), NotificationType.Error);
        }
    }

    private void validateItemMemberId(String code, TransactionType transactionType) throws Exception {
        if (code.isEmpty()) {
            if (transactionType == TransactionType.RECEIVE) {
                throw new Exception("Please enter item code");
            } else {
                throw new Exception("Please Enter item code and member identifier");
            }
        }

        for (Character c : code.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new Exception("Invalid item code or member identifier");
            }
        }
        notificationController.clearNotificationText();
    }

    private void focusChangeListener() {
        itemCodeReceive.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
        });
    }
}
