package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.exception.FXMLLoaderError;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.model.Transaction;
import com.example.javafxendassignement2022.model.User;
import com.example.javafxendassignement2022.service.ItemService;
import com.example.javafxendassignement2022.service.MainWindowService;
import com.example.javafxendassignement2022.service.MemberService;
import com.example.javafxendassignement2022.service.TransactionService;
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

// this class contains code for the main widow
// together with the login screen the main window assembles all the screens together to form one application
//Also, it listens to menu selections and displays the appropriate screen
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
    private LogoutDialogController logoutDialogController;

    public MainWindowController(User user, Stage stage) throws IOException, FXMLLoaderError {
        this.database = new ItemMemberDatabase();
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource(
                "main-window.fxml"));
        fxmlLoader.setController(this);
        fxmlLoader.load();
        this.stage = stage;
        welcomeLabel.setText("Welcome" + " " + user.getFirstName() + " " + user.getLastName());
        borderPane.setCenter(loadScene("lend-receive-item-form.fxml", lendReceiveItemController));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            initWindows();
            listenToMenuSelections();
            initLogoutController();
            handleLogoutConfirmed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // initialize controllers
    private void initWindows() {
        ItemService itemService = new ItemService(database);
        MemberService memberService = new MemberService(database);
        itemTableController = new ItemTableController(itemService, new ItemDialogFormController(itemService));
        memberTableController = new MemberTableController(memberService, new MemberDialogFormController(memberService));
        lendReceiveItemController = new LendReceiveItemController(new TransactionService(database, itemService, memberService));
        mainWindowService = new MainWindowService(database);
    }

    private void initLogoutController() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource("logoutDialog.fxml"));
        fxmlLoader.load();
        logoutDialogController = fxmlLoader.getController();
    }

    private void listenToMenuSelections() {
        menuController.getSelectedMenuBtn().addListener((observable, oldValue, newValue) -> {
            try {
                handleMenuSelections(newValue);
            } catch (FXMLLoaderError e) {
                e.printStackTrace();
            }
        });
    }

    // show user requested screen based on the selected menu button
    private void handleMenuSelections(String selectedMenu) throws FXMLLoaderError {
        if (Objects.equals(selectedMenu, menuController.collection.getText())) {
            borderPane.setCenter(loadScene("item-table.fxml", itemTableController));
        } else if (Objects.equals(selectedMenu, menuController.members.getText())) {
            borderPane.setCenter(loadScene("members-table.fxml", memberTableController));
        } else if (Objects.equals(selectedMenu, menuController.lendingReceiving.getText())) {
            borderPane.setCenter(loadScene("lend-receive-item-form.fxml", lendReceiveItemController));
        } else {
            logoutDialogController.showLogout();
        }
    }

    private void handleLogoutConfirmed() {
        logoutDialogController.logoutConfirmedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (Boolean.TRUE.equals(t1)) {
                try {
                    saveDataToFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // process logout
    private void saveDataToFile() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(LibrarySystem.class.getResource("login.fxml"));
        Parent root = fxmlLoader.load();
        LoginController loginController = fxmlLoader.getController();
        loginController.showLoginWindow((Stage) borderPane.getScene().getWindow(), root);
        // save files to database
        mainWindowService.saveListToFile("items", database.getRecords(Item.class));
        mainWindowService.saveListToFile("members", database.getRecords(Member.class));
        mainWindowService.saveListToFile("transactions", database.getRecords(Transaction.class));
    }

    // show main window
    public void showMainWindow() {
        Scene scene = new Scene(vBoxRootLayout, 800, 550);
        stage.setScene(scene);
        stage.centerOnScreen();
    }
}

