package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.model.MessageType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class NotificationController {
    @FXML
    public HBox hBoxParent;
    @FXML
    private Label notificationLabel;

    public void setNotificationText(String text, MessageType messageType) {
        notificationBackgroundColor(messageType);
        notificationLabel.setText(text);
        showHideLabel();
    }

    public void clearNotificationText() {
        if (notificationLabel != null) {
            notificationLabel.setText(null);
        }
    }

    private void showHideLabel() {
        notificationLabel.textProperty().addListener((Observable, oldText, newText) -> {
            hBoxParent.setVisible(newText != null);
        });
    }

    private void notificationBackgroundColor(MessageType messageType) {
        if (messageType.equals(MessageType.Error)) {
            hBoxParent.setStyle("-fx-background-color: rgba(255,0,0,0.61)");
        } else {
            hBoxParent.setStyle("-fx-background-color: rgba(0,128,0,0.62)");
        }
    }
}
