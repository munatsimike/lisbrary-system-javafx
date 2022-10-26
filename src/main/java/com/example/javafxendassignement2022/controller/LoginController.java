package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.UserDatabase;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.FXMLLoaderError;
import com.example.javafxendassignement2022.exception.InvalidInput;
import com.example.javafxendassignement2022.exception.InvalidPassword;
import com.example.javafxendassignement2022.exception.WrongUsernamePasswordCombination;
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

// this class contains code for the login in screen
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

    // handle on login button clicks
    @FXML
    protected void onLoginButtonClick() {
        try {
            // validate username and password
            validateUserName(usernameTxtField.getText());
            validatePassword(passwordTxtField.getText());
            // get full username if correct username and password combination have been entered
            User user = loginService.getUser(new User(usernameTxtField.getText(), passwordTxtField.getText()));
            notificationController.clearNotificationText();
            // open main window
            openManinWindow(user);
        } catch (InvalidInput | WrongUsernamePasswordCombination | IOException | InvalidPassword | FXMLLoaderError e) {
            notificationController.setNotificationText(e.toString(), NotificationType.ERROR);
        }
    }

    // open main window
    private void openManinWindow(User loggedInUser) throws IOException, FXMLLoaderError {
        Stage stage = (Stage) vBox.getScene().getWindow();
        MainWindowController mainWindowController = new MainWindowController(loggedInUser, stage);
        mainWindowController.showMainWindow();
    }

    // validate username
    private void validateUserName(String username) throws InvalidInput {
        if (username.length() < 8) {
            throw new InvalidInput("Username should contain at least 8 characters");
        }
    }

    // validate password
    private void validatePassword(String password) throws InvalidInput {
        if (password.length() < 8) {
            throw new InvalidInput("password should contain at least 8 characters");
        }
    }

    // handle on close button clicks
    @FXML
    public void onCloseButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
    }

    // show login screen with specified parameters
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