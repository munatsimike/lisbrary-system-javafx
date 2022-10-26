package com.example.javafxendassignement2022.controller;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    private final StringProperty searchQuery;
    @FXML
    private TextField searchTextField;

    public StringProperty getSearchQuery() {
        return searchQuery;
    }

    public SearchController() {
        searchQuery = new SimpleStringProperty(null);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        onTextChange();
    }

    public void onTextChange() {
        searchTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            searchQuery.set(newValue);
        });
    }

    public void setPromptText(String promptText) {
        searchTextField.setPromptText(promptText);
    }
}
