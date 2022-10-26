package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.InvalidInput;
import com.example.javafxendassignement2022.exception.InvalidTextLength;
import com.example.javafxendassignement2022.exception.MemberUnderAge;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.service.MemberService;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ResourceBundle;

// this class contains code for the dialog form which manages members: add and edit
public class MemberDialogFormController extends BaseController implements Initializable {
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
    private Stage stage;
    private final MemberService memberService;
    private final SimpleBooleanProperty memberEdited;

    public SimpleBooleanProperty memberEditedProperty() {
        return memberEdited;
    }

    public MemberDialogFormController(MemberService memberService) {
        memberEdited = new SimpleBooleanProperty(false);
        this.memberService = memberService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        stage = getWindow(vBoxParent, 500, 320);
    }

    // fill dialog form with selected member to be edited
    public void editMember(Member member) {
        memberIdentifier.setText(String.valueOf(member.getIdentifier()));
        firstName.setText(member.getFirstName());
        lastName.setText(member.getLastName());
        dateOfBirth.setValue(member.getDateOfBirth());
        notificationController.clearNotificationText();
        showForm("Edit member");
    }

    // show add dialog form
    public void addMember() {
        notificationController.clearNotificationText();
        showForm("Add member");
    }

    // handle dialog form button clicks
    public void onButtonClick(ActionEvent actionEvent) {
        if (actionEvent.getSource().equals(addMember)) {
            try {
                // validate first and lastname
                validateFirstLastName(lastName.getText().trim());
                validateFirstLastName(firstName.getText().trim());
                // process add member
                if (addMember.getText().equals(ButtonText.ADD_MEMBER.toString())) {
                    memberService.addMember(new Member(memberService.getMemberIdentifier(), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), validateDate(dateOfBirth)));
                    notificationController.setNotificationText("Member saved successfully", NotificationType.SUCCESS);
                } else {
                    // process edit member
                    memberService.editMember(new Member(Integer.parseInt(memberIdentifier.getText()), capitalizeFirstLetter(firstName.getText()), capitalizeFirstLetter(lastName.getText()), validateDate(dateOfBirth)));
                    stage.hide();
                    // indicate edited has been done
                    memberEdited.setValue(true);
                }
                clearForm();
                memberEdited.setValue(false);
            } catch (DateTimeParseException e) {
                notificationController.setNotificationText("Invalid date format, date format should be dd/mm/yyyy", NotificationType.ERROR);
            } catch (MemberUnderAge | InvalidInput | InvalidTextLength e) {
                notificationController.setNotificationText(e.toString(), NotificationType.ERROR);
            }

        } else {
            clearForm();
            stage.hide();
            notificationController.clearNotificationText();
        }
    }

    // clear form
    private void clearForm() {
        if (!memberIdentifier.getText().equals(""))
            memberIdentifier.setText("");
        if (!firstName.getText().equals(""))
            firstName.setText("");
        if (!lastName.getText().equals(""))
            lastName.setText("");
        if (dateOfBirth.getValue() != null || dateOfBirth.getEditor().getText() != null)
            dateOfBirth.setValue(null);
    }

    // show form
    private void showForm(String title) {
        stage.setTitle(title);
        stage.show();
    }

    // validate date
    private LocalDate validateDate(DatePicker datePicker) throws MemberUnderAge, InvalidInput {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        if (datePicker.getEditor().getText() != null) {
            LocalDate birthDay = LocalDate.parse(datePicker.getEditor().getText(), formatter);
            memberService.calculateAge(birthDay);
            return birthDay;
        }

        datePicker.getValue();
        throw new InvalidInput("Date of birth field is empty, please enter date of birth");
    }

    public String capitalizeFirstLetter(String str) {
        if (str == null || str.length() <= 1) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    // set dialog form button text
    public void setAddEditMemberController(ButtonText editMember) {
        addMember.setText(editMember.toString());
        if (addMember.getText().equals("Edit member")) {
            setEditButtonBackground(addMember);
        }
    }

    // validate first name
    private void validateFirstLastName(String name) throws InvalidTextLength, InvalidInput {
        validateTextLength(name);
        isAllLetters(name);
    }
}
