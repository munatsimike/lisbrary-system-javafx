package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.ItemNotFoundException;
import com.example.javafxendassignement2022.exception.MemberNotFoundException;
import com.example.javafxendassignement2022.exception.ReturnDateOverdueException;
import com.example.javafxendassignement2022.service.TransactionService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class LendReceiveItemController implements Initializable {
    @FXML
    BorderPane lendNotificationContainer;
    @FXML
    BorderPane receiveNotificationContainer;
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
    private NotificationController lendNotificationController;
    private NotificationController receiveNotificationController;
    private TransactionService transaction;

    public LendReceiveItemController(ItemMemberDatabase itemDataBase) {
        transaction = new TransactionService(itemDataBase);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lendNotificationController = initNotification(lendNotificationContainer);
            receiveNotificationController = initNotification(receiveNotificationContainer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private NotificationController initNotification(BorderPane notificationContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        HBox lendFormNotification = loader.load(Objects.requireNonNull(LibrarySystem.class.getResource("notification.fxml")).openStream());
        NotificationController controller = loader.getController();
        notificationContainer.setCenter(lendFormNotification);
        return controller;
    }

    public void onButtonClick(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                validateItemMemberId(itemCodeLend.getText(), TransactionType.LEND);
                validateItemMemberId(memberIdentifier.getText(), TransactionType.LEND);
                transaction.lend(Integer.parseInt(itemCodeLend.getText()), Integer.parseInt(memberIdentifier.getText()));
                lendNotificationController.setNotificationText("Item lent successfully", NotificationType.SUCCESS);
                clearLendForm();
            } else {
                validateItemMemberId(itemCodeReceive.getText(), TransactionType.RECEIVE);
                transaction.receive(Integer.parseInt(itemCodeReceive.getText()));
                receiveNotificationController.setNotificationText("Item successfully received and now available", NotificationType.SUCCESS);
                clearReceiveForm();
            }
        } catch (ReturnDateOverdueException e) {
            receiveNotificationController.setNotificationText(e.toString(), NotificationType.INFO);
        } catch (ItemNotFoundException | MemberNotFoundException e) {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                lendNotificationController.setNotificationText(e.toString(), NotificationType.ERROR);
            } else {
                receiveNotificationController.setNotificationText(e.toString(), NotificationType.ERROR);
            }
        } catch (Exception e) {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                lendNotificationController.setNotificationText(e.getMessage(), NotificationType.ERROR);
                receiveNotificationController.clearNotificationText();
                clearReceiveForm();
            } else {
                receiveNotificationController.setNotificationText(e.getMessage(), NotificationType.ERROR);
                lendNotificationController.clearNotificationText();
                clearLendForm();
            }
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
        lendNotificationController.clearNotificationText();
    }

    private void clearLendForm() {
        if (!Objects.equals(itemCodeLend.getText(), "")) {
            itemCodeLend.setText("");
        }

        if (!Objects.equals(memberIdentifier.getText(), "")) {
            memberIdentifier.setText("");
        }
    }

    private void clearReceiveForm() {
        if (!Objects.equals(itemCodeReceive.getText(), "")) {
            itemCodeReceive.setText("");
        }
    }
}