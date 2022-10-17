package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.UserDatabase;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.model.User;
import com.example.javafxendassignement2022.service.LoginService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
    private final LoginService loginService;

    public LoginController() {
        loginService = new LoginService(new UserDatabase());
    }

    @FXML
    protected void onLoginButtonClick() {
        try {
            validateUserName(usernameTxtField.getText());
            validatePassword(passwordTxtField.getText());
            loginService.login(new User(usernameTxtField.getText(), passwordTxtField.getText()));
            notificationController.clearNotificationText();
            openManinWindow(usernameTxtField.getText());
        } catch (Exception e) {
            notificationController.setNotificationText(e.getMessage(), NotificationType.Error);
        }
    }

    private void openManinWindow(String loggedInUser) throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        MainWindowController mainWindowController = new MainWindowController(loggedInUser, stage);
        mainWindowController.showMainWindow();
    }

    private void validateUserName(String username) throws Exception {
        if (username.length() < 8) {
            throw new Exception("Username should contain at least 8 characters");
        }
    }

    private void validatePassword(String password) throws Exception {
        if (password.length() < 8) {
            throw new Exception("password should contain at least 8 characters");
        }
    }


    @FXML
    public void onCancelButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
    }

    public void showLoginWindow(Stage stage, Parent parent) {
        Scene scene = new Scene(parent, 500, 350, Color.BLACK);
        stage.setTitle("Login");
        if (!stage.isShowing()) {
            stage.initStyle(StageStyle.UNDECORATED);
        }
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }
}