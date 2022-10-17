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
}
