package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController implements Initializable {

    @FXML
    public Label welcomeLabel;
    @FXML
    public VBox membersTable;
    @FXML
    public VBox itemsTable;
    @FXML
    public HBox lendReceiveForm;
    @FXML
    public BorderPane borderPane;
    @FXML
    private MenuController menuController;
    private FormController formController;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        borderPane.setCenter(lendReceiveForm);
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
            navigationGraph(newValue);
        });
    }

    private void observeFormButtonClicks() {
        formController.selectedButton().addListener((Observable, oldValue, newValue) -> {
            System.out.println();
        });
    }

    private void navigationGraph(String selectedMenu) {
        if (Objects.equals(selectedMenu, menuController.collection.getText())) {
            borderPane.setCenter(itemsTable);
        } else if (Objects.equals(selectedMenu, menuController.members.getText())) {
            borderPane.setCenter(membersTable);
        } else {
            borderPane.setCenter(lendReceiveForm);
        }
    }

    public void setWelcomeLabelText(String user) {
        welcomeLabel.setText("Welcome " + user);
    }
}

