package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.enums.TransactionType;
import com.example.javafxendassignement2022.exception.*;
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

// this class contains code to lend and receive items
public class LendReceiveItemController implements Initializable {
    @FXML
    BorderPane lendNotificationContainer;
    @FXML
    BorderPane receiveNotificationContainer;
    @FXML
    private TextField itemCodeLend;
    @FXML
    private TextField memberIdentifier;
    @FXML
    private TextField itemCodeReceive;
    @FXML
    private Button receiveItemBtn;
    @FXML
    private Button lendItemBtn;
    @FXML
    private Button clearLendFormBtn;
    private NotificationController lendNotificationController;
    private NotificationController receiveNotificationController;
    private final TransactionService transaction;

    public LendReceiveItemController(TransactionService transaction) {
        this.transaction = transaction;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            lendNotificationController = initNotification(lendNotificationContainer);
            receiveNotificationController = initNotification(receiveNotificationContainer);
            lendFormItemCodeFocusChange();
            receiveFormItemCodeFocusChange();
            lendFormMemberIdentifierFocusChange();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // initialize notification
    private NotificationController initNotification(BorderPane notificationContainer) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        HBox lendFormNotification = loader.load(Objects.requireNonNull(LibrarySystem.class.getResource("notification.fxml")).openStream());
        NotificationController controller = loader.getController();
        notificationContainer.setCenter(lendFormNotification);
        return controller;
    }

    // handle lend receive form button clicks
    public void onButtonClick(ActionEvent actionEvent) {
        try {
            if (actionEvent.getSource().equals(lendItemBtn)) {
                // clear notification on receive form
                receiveNotificationController.clearNotificationText();
                // validate member id and item code
                validateItemMemberId(itemCodeLend.getText(), TransactionType.LEND);
                validateItemMemberId(memberIdentifier.getText(), TransactionType.LEND);
                // process lend transactions
                transaction.lend(Integer.parseInt(itemCodeLend.getText()), Integer.parseInt(memberIdentifier.getText()));
                lendNotificationController.setNotificationText("Item lent successfully", NotificationType.SUCCESS);
                clearLendForm();
            } else if (actionEvent.getSource().equals(receiveItemBtn)) {
                // clear notification messages on the lend form
                lendNotificationController.clearNotificationText();
                // validate member id
                validateItemMemberId(itemCodeReceive.getText(), TransactionType.RECEIVE);
                transaction.receive(Integer.parseInt(itemCodeReceive.getText()));
                //process receive transaction
                receiveNotificationController.setNotificationText("Item successfully received and now available", NotificationType.SUCCESS);
                clearReceiveForm();
            } else if (actionEvent.getSource().equals(clearLendFormBtn)) {
                clearLendForm();
                lendNotificationController.clearNotificationText();
            } else {
                clearReceiveForm();
                receiveNotificationController.clearNotificationText();
            }
        } catch (ReturnDateOverdueException e) {
            receiveNotificationController.setNotificationText(e.toString(), NotificationType.INFO);
            clearReceiveForm();
        } catch (ItemNotFoundException | MemberNotFoundException | InvalidInput | EmptyField | TransactionError e) {
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

    // validate member id
    private void validateItemMemberId(String code, TransactionType transactionType) throws EmptyField, InvalidInput {
        if (code.isEmpty()) {
            if (transactionType == TransactionType.RECEIVE) {
                throw new EmptyField("Please enter item code");
            } else {
                throw new EmptyField("Please Enter item code and member identifier");
            }
        }

        for (Character c : code.toCharArray()) {
            if (!Character.isDigit(c)) {
                throw new InvalidInput("Invalid item code or member identifier");
            }
        }
    }

    // clear lend form
    private void clearLendForm() {
        if (!Objects.equals(itemCodeLend.getText(), "")) {
            itemCodeLend.setText("");
        }

        if (!Objects.equals(memberIdentifier.getText(), "")) {
            memberIdentifier.setText("");
        }
    }

    // clear receive form
    private void clearReceiveForm() {
        if (!Objects.equals(itemCodeReceive.getText(), "")) {
            itemCodeReceive.setText("");
        }
    }

    // handle item code field receive form focus change
    private void receiveFormItemCodeFocusChange() {
        itemCodeReceive.focusedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (Boolean.TRUE.equals(t1)) {
                lendNotificationController.clearNotificationText();
            }
        });
    }

    // handle member focus change
    private void lendFormMemberIdentifierFocusChange() {
        memberIdentifier.focusedProperty().addListener((observableValue, aBoolean, t1) ->
                onFocus(t1));
    }

    // handle lend form item code field focus change
    private void lendFormItemCodeFocusChange() {
        itemCodeLend.focusedProperty().addListener((observableValue, aBoolean, t1) ->
                onFocus(t1));
    }

    private void onFocus(boolean value) {
        if (Boolean.TRUE.equals(value)) {
            receiveNotificationController.clearNotificationText();
        }
    }
}