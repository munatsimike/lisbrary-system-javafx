package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

import java.util.Objects;

public class NotificationController {
    @FXML
    public HBox hBoxParent;
    @FXML
    private Label notificationLabel;

    public void setNotificationText(String text, NotificationType messageType) {
        if(text.length() == 0){
            return;
        }
        notificationBackgroundColor(messageType);
        notificationLabel.setText(text);
        showHideLabel();
    }

    public void clearNotificationText() {
        if (!Objects.equals(notificationLabel.getText(), "")) {
            notificationLabel.setText("");
        }
    }

    private void showHideLabel() {
        notificationLabel.textProperty().addListener((observable, oldText, newText) -> {
            hBoxParent.setVisible(!newText.equals(""));
        });
    }

    private void notificationBackgroundColor(NotificationType messageType) {
        if (messageType.equals(NotificationType.ERROR)) {
            hBoxParent.setStyle("-fx-background-color: rgba(255,0,0,0.61)");
        } else if (messageType.equals(NotificationType.SUCCESS)){
            hBoxParent.setStyle("-fx-background-color: rgba(0,128,0,0.62)");
        }else{
            hBoxParent.setStyle("-fx-background-color: rgb(95,14,171)");
        }
    }
}
