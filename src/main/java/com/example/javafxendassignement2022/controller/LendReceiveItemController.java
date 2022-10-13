package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemDatabase;
import com.example.javafxendassignement2022.enums.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.io.IOException;
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        focusChangeListener();
    }

    public void onButtonClick(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                validateItemMemberId(itemCodeLend.getText());
                validateItemMemberId(memberIdentifier.getText());
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
            System.out.println(aBoolean);
            System.out.println(t1);
        });
    }
}
