package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.model.Member;
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
import java.util.ResourceBundle;

public class AddEditMemberController implements Initializable {
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Scene scene = new Scene(vBoxParent, 450, 320);
        stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.WINDOW_MODAL);
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
            System.out.println("added");
        } else {
            stage.hide();
        }
        clearForm();
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
}
