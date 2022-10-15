package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.MessageType;
import com.example.javafxendassignement2022.model.Item;
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

    public LendReceiveItemController(ItemMemberDatabase itemDataBase) {
        this.itemDatabase = itemDataBase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        focusChangeListener();
    }

    public void onButtonClick(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                validateItemMemberId(itemCodeLend.getText());
                validateItemMemberId(memberIdentifier.getText());
                itemDatabase.addItem(new Item(1,"yes", "yes", "yes"));
            } else {
                validateItemMemberId(itemCodeReceive.getText());
            }
        } catch (Exception e) {
            notificationController.setNotificationText(e.getMessage(), MessageType.Error);
        }
    }

    private void validateItemMemberId(String code) throws Exception {
        if (code.isEmpty()) {
            throw new Exception("All fields are mandatory");
        }

        for (Character c : code.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new Exception("Invalid item code or member identifier");
            }
        }
        notificationController.clearNotificationText();
    }
    private void focusChangeListener(){
        itemCodeReceive.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            System.out.println(t1);
        });
    }
}
