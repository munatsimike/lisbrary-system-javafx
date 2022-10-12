package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ToggleButton;

import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private final StringProperty selectedBtn;
    private List<ToggleButton> menuButtons;

    public StringProperty getSelectedMenuBtn() {
        return selectedBtn;
    }

    @FXML
    ToggleButton lendingReceiving;
    @FXML
    ToggleButton collection;
    @FXML
    ToggleButton members;

    public MenuController() {
        selectedBtn = new SimpleStringProperty(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        lendingReceiving.setSelected(true);
        menuButtons = List.of(collection, members, lendingReceiving);
        onActionHandler();
    }

    // handle menu toggle action buttons
    public void onActionHandler() {
        // check if members is selected
        if (members.isSelected()) {
            selectedBtn.set(members.getText());
            changBackgroundColor(members);
            // check if collection is selected
        } else if (collection.isSelected()) {
            selectedBtn.set(collection.getText());
            changBackgroundColor(collection);
        } else if (lendingReceiving.isSelected()) {
            selectedBtn.set(lendingReceiving.getText());
            changBackgroundColor(lendingReceiving);
        }
    }

    // set menu button background color
    public void changBackgroundColor(ToggleButton button) {
        for (ToggleButton btn : menuButtons) {
            if (Objects.equals(btn.getText(), button.getText())) {
                if (!btn.getStyleClass().contains("light-blue-background-color")) {
                    btn.getStyleClass().add("light-blue-background-color");
                }
            } else {
                btn.getStyleClass().remove("light-blue-background-color");
            }
        }
    }
}