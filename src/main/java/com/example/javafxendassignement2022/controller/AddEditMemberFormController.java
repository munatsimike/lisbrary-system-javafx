package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

public class AddEditMemberFormController extends BaseController implements Initializable {
    @FXML
    private Label memberIdentifier;
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
        memberIdentifier.setText(String.valueOf(member.getIdentifier()));
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        dateOfBirth.setValue(member.getDateOfBirth());
        notificationController.clearNotificationText();
        showForm("Edit member");
    }

    public void addMember() {
        notificationController.clearNotificationText();
        showForm("Add member");
    }

    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addMember)) {
            try {
                validateField(lastName.getText().trim());
                validateField(firstName.getText().trim());
                LocalDate birthDay = date(dateOfBirth);
                if (addMember.getText().equals(ButtonText.ADD_MEMBER.toString())) {
                    memberDatabase.addRecord(new Member(memberDatabase.getMemberIdentifier(), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), birthDay));
                    notificationController.setNotificationText("Member saved successfully", NotificationType.Success);
                } else {
                    memberDatabase.editMember(new Member(Integer.parseInt(memberIdentifier.getText()), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), birthDay));
                    notificationController.setNotificationText("Member edited successfully", NotificationType.Success);
                }
                clearForm();
            } catch (DateTimeParseException e) {
                notificationController.setNotificationText("Invalid date format, date format should be dd/mm/yyyy", NotificationType.Error);
            } catch (Exception e) {
                notificationController.setNotificationText(e.getMessage(), NotificationType.Error);
            }
        } else {
            clearForm();
            stage.hide();
            notificationController.clearNotificationText();
        }
    }

    private void clearForm() {
        memberIdentifier.setText(null);
        firstName.setText(null);
        lastName.setText(null);
        dateOfBirth.setValue(null);
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    private LocalDate date(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        if (datePicker.getValue() == null) {
            return LocalDate.parse(datePicker.getEditor().getText(), formatter);
        }
        return datePicker.getValue();
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.length() <= 1) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void setAddEditMemberController(ButtonText editMember) {
        addMember.setText(editMember.toString());
    }
}
