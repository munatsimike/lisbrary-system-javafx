package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.time.Period;
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
    public SimpleBooleanProperty operationCompleted;

    public AddEditMemberFormController(ItemMemberDatabase memberDatabase) {
        operationCompleted = new SimpleBooleanProperty(false);
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
                LocalDate birthDay = validateDate(dateOfBirth);
                if (addMember.getText().equals(ButtonText.ADD_MEMBER.toString())) {
                    memberDatabase.addRecord(new Member(memberDatabase.getMemberIdentifier(), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), birthDay));
                    notificationController.setNotificationText("Member saved successfully", NotificationType.Success);
                } else {
                    memberDatabase.editMember(new Member(Integer.parseInt(memberIdentifier.getText()), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), birthDay));
                    notificationController.setNotificationText("Member edited successfully", NotificationType.Success);
                    operationCompleted.setValue(true);
                }
                clearForm();
                operationCompleted.setValue(false);
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
        if (!memberIdentifier.getText().equals(""))
            memberIdentifier.setText("");
        if (!firstName.getText().equals(""))
            firstName.setText("");
        if (!lastName.getText().equals(""))
            lastName.setText("");
        if (dateOfBirth.getValue() != null)
            dateOfBirth.setValue(LocalDate.parse(dateOfBirth.getPromptText()));
    }

    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    private LocalDate validateDate(DatePicker datePicker) throws Exception {
        LocalDate date;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/M/yyyy");
        if (datePicker.getValue() == null) {
            date = LocalDate.parse(datePicker.getEditor().getText(), formatter);
            calculateYears(date);
            return date;
        }
        date = LocalDate.parse(datePicker.getValue().format(formatter), formatter);
        calculateYears(date);
        return date;
    }

    private void calculateYears(LocalDate dateOfBirth) throws Exception {
        Period period = dateOfBirth.until(LocalDate.now());
        if (period.getYears() < 13) {
            throw new Exception("Members should be 12 years and above");
        }
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.length() <= 1) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void setAddEditMemberController(ButtonText editMember) {
        addMember.setText(editMember.toString());
    }
}
