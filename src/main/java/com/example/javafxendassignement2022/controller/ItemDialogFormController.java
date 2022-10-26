package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.enums.Availability;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.EmptyField;
import com.example.javafxendassignement2022.exception.InvalidInput;
import com.example.javafxendassignement2022.exception.InvalidTextLength;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.service.ItemService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

// this class contains code that forms the dialog form that is used to manage items: add and edit
public class ItemDialogFormController extends BaseController implements Initializable {
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
    private Label itemCode;
    private Stage stage;
    private final ItemService itemService;
    private SimpleBooleanProperty itemEdited;

    public SimpleBooleanProperty itemEditedProperty() {
        return itemEdited;
    }

    public ItemDialogFormController(ItemService itemService) {
        itemEdited = new SimpleBooleanProperty(false);
        this.itemService = itemService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = getWindow(vBoxParent, 450, 320);
    }

    // fill the dialog form with selected item to edit
    public void editItem(Item item) {
        itemCode.setText(String.valueOf(item.getItemCode()));
        author.setText(item.getAuthor());
        title.setText(item.getTitle());
        availableCombox.setValue(item.getAvailable().toString());
        notificationController.clearNotificationText();
        showForm("Edit item");
    }

    // show edit form
    public void addItem() {
        notificationController.clearNotificationText();
        showForm("Add item");
    }

    // handle button on click events
    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addButton)) {
            try {
                // validate user inputs
                validateTitle(title.getText().trim());
                validateAuthor(author.getText().trim());
                validateAvailability(availableCombox);

                if (addButton.getText().equals(ButtonText.ADD_ITEM.toString())) {
                    // save item to the database
                    itemService.addItem(new Item(itemService.getItemCode(), Availability.valueOf(availableCombox.getValue().toUpperCase()), title.getText(), author.getText()));
                    // display user added success notification
                    notificationController.setNotificationText("Item saved successfully", NotificationType.SUCCESS);
                } else {
                    // edit item
                    itemService.editItem(new Item(Integer.parseInt(itemCode.getText()), Availability.valueOf(availableCombox.getValue().toUpperCase()), title.getText(), author.getText()));
                    // indicate completion of edit operation
                    itemEdited.setValue(true);
                    // close window
                    stage.hide();
                }
                clearForm();
                itemEdited.setValue(false);
            } catch (InvalidInput | InvalidTextLength | EmptyField e) {
                notificationController.setNotificationText(e.toString(), NotificationType.ERROR);
            }

        } else {
            stage.hide();
            clearForm();
        }
    }

    private void clearForm() {
        if (!Objects.equals(title.getText(), ""))
            title.setText("");
        if (!Objects.equals(author.getText(), ""))
            author.setText("");
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    // set button text
    public void setAddEditButtonText(ButtonText buttonText) {
        addButton.setText(buttonText.toString());
        if (addButton.getText().equals("Edit item")) {
            setEditButtonBackground(addButton);
        }
    }

    private void validateAvailability(ComboBox<String> comboBox) throws EmptyField {
        if (comboBox.getValue() == null) {
            throw new EmptyField("Availability not selected, please select availability");
        }
    }

    private void validateAuthor(String author) throws InvalidTextLength, InvalidInput {
        validateTextLength(author);
        isAllLetters(author);
    }

    private void validateTitle(String title) throws InvalidTextLength, InvalidInput {
        validateTextLength(title);
        for (char c : title.toCharArray()) {
            if (!Character.isDigit(c) && !Character.isLetter(c) && !Character.isSpaceChar(c)) {
                throw new InvalidInput("Only letters numbers or space character are allowed for title");
            }
        }
    }
}
