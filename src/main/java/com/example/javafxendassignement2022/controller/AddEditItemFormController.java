package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.util.ResourceBundle;

public class AddEditItemFormController extends BaseController implements Initializable {
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
    private TextField title;
    @FXML
    private NotificationController notificationController;
    @FXML
    private Label itemCode;
    private Stage stage;
    private final ItemMemberDatabase itemDataBase;
    private boolean hasItemChanged = false;
    public SimpleBooleanProperty operationCompleted;

    public AddEditItemFormController(ItemMemberDatabase itemsDatabase) {
        operationCompleted = new SimpleBooleanProperty(false);
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
        itemCode.setText(String.valueOf(item.getItemCode()));
        author.setText(item.getAuthor());
        title.setText(item.getTitle());
        availableCombox.setValue(item.getAvailable().toString());
        notificationController.clearNotificationText();
        showForm("Edit item");
    }

    public void addItem() {
        notificationController.clearNotificationText();
        showForm("Add item");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addButton)) {
            try {
                validateField(title.getText().trim());
                validateField(author.getText().trim());
                if (addButton.getText().equals(ButtonText.ADD_ITEM.toString())) {
                    itemDataBase.addRecord(new Item(itemDataBase.getItemCode(), Availability.valueOf(availableCombox.getValue()), title.getText(), author.getText()));
                    notificationController.setNotificationText("Item saved successfully", NotificationType.Success);
                } else {
                    itemDataBase.editItem(new Item(Integer.parseInt(itemCode.getText()), Availability.valueOf(availableCombox.getValue()), title.getText(), author.getText()));
                    notificationController.setNotificationText("Item edited successfully", NotificationType.Success);
                    operationCompleted.setValue(true);
                }
                clearForm();
                operationCompleted.setValue(false);
            } catch (Exception e) {
                notificationController.setNotificationText(e.getMessage(), NotificationType.Error);
            }

        } else {
            stage.hide();
            clearForm();
        }
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
