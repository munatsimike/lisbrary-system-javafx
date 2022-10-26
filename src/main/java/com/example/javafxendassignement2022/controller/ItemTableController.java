package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemTableController extends BaseController implements Initializable {

    @FXML
    private TableView<Item> itemTable;
    @FXML
    private SearchController searchController;
    @FXML
    private FormController formController;
    @FXML
    private NotificationController notificationController;
    private ItemDialogFormController itemDialogFormController;
    private ObservableList<Item> selectedItems;
    private TableView.TableViewSelectionModel<Item> selectionModel;
    private FilteredList<Item> filteredData;
    private final ItemMemberDatabase itemsDatabase;

    public ItemTableController(ItemMemberDatabase itemDataBase) {
        this.itemsDatabase = itemDataBase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredData = new FilteredList<>(itemsDatabase.getRecords(Item.class));
        formController.setButtonText("item");
        itemTable.setItems(filteredData);
        initItemFormController();
        setSelectionMode();
        searchQueryListener();
        formButtonListener();
        clearNotification();
        refreshTable();
    }

    private void initItemFormController() {
        itemDialogFormController = new ItemDialogFormController(itemsDatabase);
        loadScene("manage-item-form.fxml", itemDialogFormController);
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.getDeleteButton().getText())) {
                if (selectedItems.size() == 1) {
                    itemsDatabase.deleteRecord(selectedItems.get(0).getItemCode(),Item.class);
                } else {
                    notificationController.setNotificationText("No item selected, select an item to delete", NotificationType.ERROR);
                }
            } else if (Objects.equals(newValue, formController.getEditButton().getText())) {
                try {
                    if (selectedItems.size() == 1) {
                        itemDialogFormController.setAddEditButtonText(ButtonText.EDIT_ITEM);
                        editItem(selectedItems.get(0));
                    } else {
                        notificationController.setNotificationText("No item selected, select an item to edit", NotificationType.ERROR);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Objects.equals(newValue, formController.getAddButton().getText())) {
                notificationController.clearNotificationText();
                itemDialogFormController.setAddEditButtonText(ButtonText.ADD_ITEM);
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
        itemDialogFormController.editItem(item);
    }

    private void addItem() {
        clearTableSelection();
        itemDialogFormController.addItem();
    }

    private void refreshTable(){
        itemDialogFormController.operationCompleted.addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                itemTable.refresh();
            }
        });
    }
}

