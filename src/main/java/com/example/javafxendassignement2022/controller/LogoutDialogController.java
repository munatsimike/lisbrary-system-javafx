package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LogoutDialogController extends BaseController implements Initializable {
    @FXML
    private VBox vBoxParent;
    @FXML
    private Button logoutButton;
    @FXML
    private Button cancelButton;
    private Stage stage;
    private SimpleBooleanProperty logoutConfirmed;

    public SimpleBooleanProperty logoutConfirmedProperty() {
        return logoutConfirmed;
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(logoutButton)) {
            logoutConfirmed.setValue(true);
        }
        stage.hide();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = getWindow(vBoxParent, 400, 100);
        logoutConfirmed = new SimpleBooleanProperty(false);
    }

    public void showLogout() {
        stage.show();
    }
}
