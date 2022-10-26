package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import java.net.URL;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;

public class MenuController extends BaseController implements Initializable {
    @FXML
    public Label logout;
    @FXML
    ToggleButton lendingReceiving;
    @FXML
    ToggleButton collection;
    @FXML
    ToggleButton members;

    private final StringProperty selectedBtn;
    private List<ToggleButton> menuButtons;
    public StringProperty getSelectedMenuBtn() {
        return selectedBtn;
    }

    public MenuController() {
        selectedBtn = new SimpleStringProperty(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // by default the lending and receiving form will be selected
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
            // check if the lending and receiving form is selected
        } else if (lendingReceiving.isSelected()) {
            selectedBtn.set(lendingReceiving.getText());
            changBackgroundColor(lendingReceiving);
        }
    }

    // set menu button background color
    public void changBackgroundColor(ToggleButton button) {
        for (ToggleButton btn : menuButtons) {
            if (Objects.equals(btn.getText(), button.getText())) {
                if (!btn.getStyleClass().contains(LIGHT_BLUE_BACKGROUND)) {
                    btn.getStyleClass().add(LIGHT_BLUE_BACKGROUND);
                }
            } else {
                btn.getStyleClass().remove(LIGHT_BLUE_BACKGROUND);
            }
        }
    }

  @FXML
    public void onMouseClicked() {
        selectedBtn.setValue(logout.getText());
    }
}