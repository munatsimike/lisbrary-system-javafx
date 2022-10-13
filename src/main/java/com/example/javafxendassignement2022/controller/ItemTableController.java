package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.enums.MessageType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemTableController implements Initializable {

    private ObservableList<Item> selectedItems;
    private TableView.TableViewSelectionModel<Item> selectionModel;
    private FilteredList<Item> filteredData;
    private ItemDatabase itemsDatabase;

    @FXML
    private TableView<Item> itemTable;
    @FXML
    private SearchController searchController;
    @FXML
    private FormController formController;
    @FXML
    private NotificationController notificationController;
    private AddEditItemFormController addEditFromController;

    public ItemTableController() {
        itemsDatabase = new ItemDatabase();
        filteredData = new FilteredList<>(itemsDatabase.getItems());
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(itemTable);
        System.out.println(itemsDatabase);
        System.out.println(filteredData);
        // Configure the TableView
        initItemFormController();
        setSelectionMode();
        searchQueryListener();
        formButtonListener();
    }

    private void initItemFormController() {
        itemTable.setItems(filteredData);
        FXMLLoader loader = new FXMLLoader(LibrarySystem.class.getResource("add-edit-item-form.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEditFromController = loader.getController();
        addEditFromController.iniDatabase(itemsDatabase);
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.deleteButton.getText())) {
                if (selectedItems.size() == 1) {
                    itemsDatabase.deleteItem(selectedItems.get(0).getItemCode());
                } else {
                    notificationController.setNotificationText("No item selected, select an item to delete", MessageType.Error);
                }
            } else if (Objects.equals(newValue, formController.editButton.getText())) {
                try {
                    if (selectedItems.size() == 1) {
                        addEditFromController.setAddEditButtonText(ButtonText.EDIT_ITEM);
                        editItem(selectedItems.get(0));
                    } else {
                        notificationController.setNotificationText("No item selected, select an item to edit", MessageType.Error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Objects.equals(newValue, formController.addButton.getText())) {
                notificationController.clearNotificationText();
                addEditFromController.setAddEditButtonText(ButtonText.ADD_ITEM);
                addItem();
            }
        }));
    }

    private void searchQueryListener() {
        searchController.getSearchQuery().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(createPredicate(newValue));
        });
    }

    private void setSelectionMode() {
        selectionModel = itemTable.getSelectionModel();
        selectedItems = selectionModel.getSelectedItems();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    private void clearNotification() {
        selectedItems.addListener((ListChangeListener<Item>) change -> {
            notificationController.clearNotificationText();
        });
    }

    // clear selected row
    public void clearTableSelection() {
        selectionModel.clearSelection();
    }

    private boolean searchFindItem(Item item, String searchText) {
        return (item.getTitle().toLowerCase().startsWith(searchText.toLowerCase())) ||
                (item.getAuthor().toLowerCase().startsWith(searchText.toLowerCase()));
    }

    private Predicate<Item> createPredicate(String searchText) {
        return item -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindItem(item, searchText.trim());
        };
    }

    private void editItem(Item item) throws IOException {
        clearTableSelection();
        addEditFromController.editItem(item);
    }

    private void addItem() {
        clearTableSelection();
        addEditFromController.addItem();
    }
}
