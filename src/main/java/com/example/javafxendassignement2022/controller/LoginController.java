package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.UserDatabase;
import com.example.javafxendassignement2022.enums.MessageType;
import com.example.javafxendassignement2022.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    @FXML
    public VBox vBox;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField passwordTxtField;
    @FXML
    private TextField usernameTxtField;
    @FXML
    private NotificationController notificationController;
    private final UserDatabase userDatabase;
    private final FXMLLoader loader;

    public LoginController() {
        loader = new FXMLLoader();
        userDatabase = new UserDatabase();
    }

    @FXML
    protected void onLoginButtonClick() {
        try {
            validateUserName(usernameTxtField.getText());
            validatePassword(passwordTxtField.getText());
            userDatabase.findUser(new User(usernameTxtField.getText(), passwordTxtField.getText()));
            openManinWindow(usernameTxtField.getText());
        } catch (Exception e) {
            notificationController.setNotificationText(e.getMessage(), MessageType.Error);
        }
    }

    private void openManinWindow(String loggedInUser) throws IOException {
        loader.setLocation(LibrarySystem.class.getResource("main-window.fxml"));
        Parent root = loader.load();
        MainWindowController mainWindowController = loader.getController();
        mainWindowController.setWelcomeLabelText(loggedInUser);
        Scene scene = new Scene(root, 800, 550);
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    private void validatePassword(String password) throws Exception {
        if (password.length() < 8) {
            throw new Exception("password should contain at least 8 characters");
        } else if (!password.matches(".*[A-Z].*")) {
            throw new Exception("password must contain at least 1 uppercase letter");
        } else if (!password.matches(".*[a-z].*")) {
            throw new Exception("password must contain at least 1 lowercase letter");
        } else if (!password.matches(".*[@#*\\$%^&+=].*")) {
            throw new Exception("password must contain at least 1 special character: [@#*$&+=]");
        } else {
            notificationController.clearNotificationText();
        }
    }

    private void validateUserName(String username) throws Exception {
        if (username.length() < 8) {
            throw new Exception("Username should contain at least 8 characters");
        } else if (!username.matches("^[0-9a-zA-Z]+$")) {
            throw new Exception("Username must contain letters and numbers only");
        } else {
            notificationController.clearNotificationText();
        }
    }

    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
    }
}