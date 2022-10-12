package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class FormController  {
    private final StringProperty selectedButton;
    @FXML
    public Button deleteButton;
    @FXML
    public Button editButton;
    @FXML
    public Button addButton;

    public StringProperty selectedButton() {
        return selectedButton;
    }

    public FormController() {
        selectedButton = new SimpleStringProperty(null);
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(deleteButton)) {
            selectedButton.set(deleteButton.getText());
        } else if (actionEvent.getSource().equals(editButton)) {
            selectedButton.set(editButton.getText());
        } else {
            selectedButton.set(addButton.getText());
        }
        selectedButton.set(null);
    }
}
