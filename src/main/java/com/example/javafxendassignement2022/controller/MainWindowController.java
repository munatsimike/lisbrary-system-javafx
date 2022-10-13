package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemDatabase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {
    @FXML
    public Label welcomeLabel;
    @FXML
    public VBox membersTableContainer;
    @FXML
    public VBox itemsTableContainer;
    @FXML
    public HBox lendReceiveItemFormContainer;
    @FXML
    public BorderPane borderPane;
    @FXML
    private MenuController menuController;
    private FormController formController;
    private final ItemDatabase itemDataBase;

    public MainWindowController() {
        this.itemDataBase = new ItemDatabase();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPane.setCenter(lendReceiveItemFormContainer);
        observeMenuItemChanges();
        initializeForm();
        observeFormButtonClicks();
    }

    private void initializeForm() {
        FXMLLoader loader = new FXMLLoader(LibrarySystem.class.getResource("bottom-form.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        formController = loader.getController();
    }

    private void observeMenuItemChanges() {
        menuController.getSelectedMenuBtn().addListener((Observable, oldValue, newValue) -> {
            try {
                navigationGraph(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void observeFormButtonClicks() {
        formController.selectedButton().addListener((Observable, oldValue, newValue) -> {
            System.out.println();
        });
    }

    private void navigationGraph(String selectedMenu) throws IOException {
        if (Objects.equals(selectedMenu, menuController.collection.getText())) {
            borderPane.setCenter(itemsTableContainer);
        } else if (Objects.equals(selectedMenu, menuController.members.getText())) {
            borderPane.setCenter(membersTableContainer);
        } else if (Objects.equals(selectedMenu, menuController.lendingReceiving.getText())) {
            borderPane.setCenter(lendReceiveItemFormContainer);
        } else {
            logout();
        }
    }

    private void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 500, 350);
        Stage stage = (Stage) borderPane.getScene().getWindow();
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void setWelcomeLabelText(String user) {
        welcomeLabel.setText("Welcome " + user);
    }
}

