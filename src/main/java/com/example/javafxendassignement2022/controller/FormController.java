package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class FormController  {
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

    public StringProperty selectedButton() {
        return selectedButton;
    }

    public FormController() {
        selectedButton = new SimpleStringProperty(null);
    }

    public void setButtonText(String name){
        deleteButton.setText(deleteButton.getText() + name);
        editButton.setText(editButton.getText() + name);
        addButton.setText(addButton.getText() + name);
    }

    public void showHideButtons(){
        formButtonRoot.setVisible(false);
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
