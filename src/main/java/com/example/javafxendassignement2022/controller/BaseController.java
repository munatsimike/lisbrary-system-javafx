package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.FXMLLoaderError;
import com.example.javafxendassignement2022.exception.InvalidInput;
import com.example.javafxendassignement2022.exception.InvalidTextLength;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

// this class contains common methods that can be used by controllers
public abstract class BaseController {
    static final String LIGHT_BLUE_BACKGROUND = "light-blue-background-color";
    @FXML
    protected NotificationController notificationController;
    @FXML
    protected SearchController searchController;
    @FXML
    protected ManageItemMemberButtons formController;

    // used to assign controllers to fxml files
    protected Node loadScene(String name, javafx.fxml.Initializable controller) throws FXMLLoaderError {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource(name));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new FXMLLoaderError(e.getMessage());
        }
    }

    // validate if input from use is letters of the alphabet
    protected void isAllLetters(String text) throws InvalidInput {
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c) && !Character.isSpaceChar(c)) {
                if (this instanceof MemberDialogFormController) {
                    throw new InvalidInput("First and Last name should only contain letters of the alphabet");
                }
                throw new InvalidInput("Author should only contain letters of the alphabet");
            }
        }
    }

    // validate text length
    protected void validateTextLength(String text) throws InvalidTextLength {
        if (text.length() < 3) {
            if (this instanceof MemberDialogFormController) {
                throw new InvalidTextLength("First and last name must contain at least 3 letters");
            }
            throw new InvalidTextLength("Author and title must contain at least 3 letters");
        }
    }

    // change background color of the edit button on dialog forms
    protected void setEditButtonBackground(Button addButton) {
        addButton.setStyle("-fx-background-color: #d94a0d");
    }

    // refresh table and show success message after editing  an item or member
    protected <T> void onEditCompleted(TableView<T> tableView) {
        String name = "Item";
        if (this instanceof MemberTableController) {
            name = "Member";
        }
        tableView.refresh();
        notificationController.setNotificationText(name + " edited successfully", NotificationType.SUCCESS);
    }

    protected Stage getWindow(Parent parent, int width, int height) {
        Scene scene = new Scene(parent, width, height);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.centerOnScreen();
        return stage;
    }
}

