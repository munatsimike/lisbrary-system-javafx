package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditItemController implements Initializable {
    @FXML
    private ComboBox<Boolean> available;
    @FXML
    private TextField author;
    @FXML
    private Button addButton;
    @FXML
    private Button cancelButton;
    @FXML
    private VBox vBoxParent;
    @FXML
    TextField title;
    @FXML
    private NotificationController notificationController;
    private Stage stage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene scene = new Scene(vBoxParent, 450, 320);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void editItem(Item item) {
        author.setText(item.getAuthor());
        title.setText(item.getTitle());
        available.setValue(item.getAvailable());
        showForm("Edit item");
    }

    public void addItem() {
        showForm("Add item");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addButton)) {
            notificationController.setNotificationText("add", MessageType.Success);
        } else {
            stage.hide();
        }
        clearForm();
    }

    private void clearForm() {
        title.setText(null);
        author.setText(null);
        available.setValue(null);
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    private void validateFirstLastName(){

    }
}
