package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.enums.MessageType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddEditMemberFormController implements Initializable {
    @FXML
    private TextField firstName;
    @FXML
    private VBox vBoxParent;
    @FXML
    private TextField lastName;
    @FXML
    private Button addMember;
    @FXML
    private Button cancel;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    NotificationController notificationController;
    private Stage stage;
    private ItemMemberDatabase memberDatabase;

    public AddEditMemberFormController(ItemMemberDatabase memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene scene = new Scene(vBoxParent, 500, 320);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
    }

    public void editMember(Member member) {
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        dateOfBirth.setValue(member.getDateOfBirth());
        showForm("Edit member");
    }

    public void addMember() {
        showForm("Add member");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addMember)) {
            try {
                if (addMember.getText().equals(ButtonText.ADD_MEMBER.toString())) {
                    validateFirstLastName(lastName.getText());
                    validateFirstLastName(firstName.getText());
                    memberDatabase.addMember(new Member(memberDatabase.getMemberIdentifier(), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), date(dateOfBirth)));
                    notificationController.setNotificationText("Member saved successfully", MessageType.Success);
                    clearForm();
                } else {
                    notificationController.setNotificationText("Member edited successfully", MessageType.Success);
                }
            } catch (DateTimeParseException e) {
                notificationController.setNotificationText("Invalid date format", MessageType.Error);
            } catch (Exception e) {
                notificationController.setNotificationText(e.getMessage(), MessageType.Error);
            }
        } else {
            clearForm();
            stage.hide();
            notificationController.clearNotificationText();
        }
    }

    private void clearForm() {
        firstName.setText(null);
        lastName.setText(null);
        dateOfBirth.setValue(null);
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    private void validateFirstLastName(String text) throws Exception {
        if (text.length() < 3) {
            throw new Exception("First and last name must contain at least 3 letters");
        }

        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c)) {
                throw new Exception("First and Last name should only contain letter of the alphabet");
            }
        }
        notificationController.clearNotificationText();
    }

    private LocalDate date(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM uuuu");
        if (datePicker.getValue() == null) {
            return LocalDate.parse(datePicker.getEditor().getText(), formatter);
        }
        return LocalDate.parse(datePicker.getValue().format(formatter), formatter);
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.length() <= 1) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void setAddEditMemberController(ButtonText editMember) {
        addMember.setText(editMember.toString());
    }
}
