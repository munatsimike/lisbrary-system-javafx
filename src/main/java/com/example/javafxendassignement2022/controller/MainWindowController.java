package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.model.Transaction;
import com.example.javafxendassignement2022.service.MainWindowService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Initializable {
    @FXML
    private Label welcomeLabel;
    @FXML
    private VBox vBoxRootLayout;
    @FXML
    public BorderPane borderPane;
    @FXML
    private MenuController menuController;
    private ItemMemberDatabase database;
    private final Stage stage;
    private MemberTableController memberTableController;
    private ItemTableController itemTableController;
    private LendReceiveItemController lendReceiveItemController;
    private MainWindowService mainWindowService;


    public MainWindowController(String welcomeText, Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource(
                "main-window.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.stage = stage;
        welcomeLabel.setText("Welcome " + welcomeText);
        borderPane.setCenter(loadScene("lend-receive-item-form.fxml", lendReceiveItemController));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.database = new ItemMemberDatabase();
        itemTableController = new ItemTableController(database);
        memberTableController = new MemberTableController(database);
        lendReceiveItemController = new LendReceiveItemController(database);
        mainWindowService = new MainWindowService(database);
        observeMenuItemChanges();
    }

    private void observeMenuItemChanges() {
        menuController.getSelectedMenuBtn().addListener((Observable, oldValue, newValue) -> {
            try {
                navigationGraph(newValue);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    private void navigationGraph(String selectedMenu) throws IOException {
        if (Objects.equals(selectedMenu, menuController.collection.getText())) {
            borderPane.setCenter(loadScene("item-table.fxml", itemTableController));
        } else if (Objects.equals(selectedMenu, menuController.members.getText())) {
            borderPane.setCenter(loadScene("members-table.fxml", memberTableController));
        } else if (Objects.equals(selectedMenu, menuController.lendingReceiving.getText())) {
            borderPane.setCenter(loadScene("lend-receive-item-form.fxml", lendReceiveItemController));
        } else {
            logout();
        }
    }

    private void logout() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.showLoginWindow((Stage) borderPane.getScene().getWindow(), root);
        mainWindowService.saveListToFile("items", database.getRecords(Item.class));
        mainWindowService.saveListToFile("members", database.getRecords(Member.class));
        mainWindowService.saveListToFile("transactions", database.getRecords(Transaction.class));
    }

    public void showMainWindow() {
        Scene scene = new Scene(vBoxRootLayout, 800, 550);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}

