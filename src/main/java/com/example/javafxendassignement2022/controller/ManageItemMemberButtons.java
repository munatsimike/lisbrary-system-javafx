package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

import java.net.URL;
import java.util.ResourceBundle;

// this class contains for the manage buttons: edit, add and delete
public class ManageItemMemberButtons implements Initializable {
    private final StringProperty selectedButton;
    @FXML
    private Button deleteButton;
    @FXML
    private Button editButton;
    @FXML
    private Button addButton;
    @FXML
    private HBox formButtonRoot;

    public Button getDeleteButton() {
        return deleteButton;
    }

    public Button getEditButton() {
        return editButton;
    }

    public Button getAddButton() {
        return addButton;
    }

    // indicator to store selected button
    public StringProperty selectedButton() {
        return selectedButton;
    }

    public ManageItemMemberButtons() {
        selectedButton = new SimpleStringProperty(null);
    }

    // set button text item or member
    public void setButtonText(String name) {
        deleteButton.setText(deleteButton.getText() + name);
        editButton.setText(editButton.getText() + name);
        addButton.setText(addButton.getText() + name);
    }

    // hide buttons
    public void showHideButtons() {
        formButtonRoot.setVisible(true);
    }

    // handle button clicks
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        showHideButtons();
    }
}
