package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;

import java.io.IOException;

public abstract class BaseController {

    protected Node loadScene(String name, javafx.fxml.Initializable controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource(name));
            fxmlLoader.setController(controller);
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected void isAllLetters(String text) throws Exception {
        for (char c : text.toCharArray()) {
            if (!Character.isLetter(c)) {
                if (this instanceof AddEditMemberFormController) {
                    throw new Exception("First and Last name should only contain letters of the alphabet");
                }
                throw new Exception("Author and title name should only contain letters of the alphabet");
            }
        }
    }

        protected void validateField (String text) throws Exception {
            if (text.length() < 3) {
                if (this instanceof AddEditMemberFormController) {
                    throw new Exception("First and last name must contain at least 3 letters");
                }
                throw new Exception("Author and title must contain at least 3 letters");
            }
            isAllLetters(text);
        }
    }
