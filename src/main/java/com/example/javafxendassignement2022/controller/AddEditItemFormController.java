package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.enums.NotificationType;
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

public class AddEditItemFormController implements Initializable {
    @FXML
    private ComboBox<String> availableCombox;
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
    private final ItemMemberDatabase itemDataBase;

    public AddEditItemFormController(ItemMemberDatabase itemsDatabase) {
        this.itemDataBase = itemsDatabase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene scene = new Scene(vBoxParent, 450, 320);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    private void initComboBoxListener() {
        availableCombox.getSelectionModel().getSelectedItem();
    }

    public void editItem(Item item) {
        author.setText(item.getAuthor());
        title.setText(item.getTitle());
        availableCombox.setValue(item.getAvailable().toString());
        showForm("Edit item");
    }

    public void addItem() {
        showForm("Add item");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addButton)) {
            try {
                if (addButton.getText().equals(ButtonText.ADD_ITEM.toString())) {
                    itemDataBase.addItem(new Item(itemDataBase.getItemCode(), Availability.valueOf(availableCombox.getValue()), title.getText(), author.getText()));
                    notificationController.setNotificationText("Item saved successfully", NotificationType.Success);
                    System.out.println("added");
                } else {
                    System.out.println("edited");
                    notificationController.setNotificationText("Item edited successfully", NotificationType.Success);

                }
            } catch (Exception e) {
                notificationController.setNotificationText(e.getMessage(), NotificationType.Error);
            }

        } else {
            stage.hide();
        }
        clearForm();
    }

    private void clearForm() {
        title.setText(null);
        author.setText(null);
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    public void setAddEditButtonText(ButtonText buttonText) {
        addButton.setText(buttonText.toString());
    }
}
