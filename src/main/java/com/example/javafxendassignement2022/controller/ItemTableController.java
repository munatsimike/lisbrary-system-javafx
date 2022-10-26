package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.FXMLLoaderError;
import com.example.javafxendassignement2022.model.Item;
import com.example.javafxendassignement2022.service.ItemService;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

public class ItemTableController extends BaseController implements Initializable {
    @FXML
    private TableView<Item> itemTable;
    private final ItemDialogFormController itemDialogFormController;
    private ObservableList<Item> selectedItems;
    private TableView.TableViewSelectionModel<Item> selectionModel;
    private FilteredList<Item> filteredData;
    private final ItemService itemService;

    public ItemTableController(ItemService itemService, ItemDialogFormController itemDialogFormController) {
        this.itemService = itemService;
        this.itemDialogFormController = itemDialogFormController;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredData = new FilteredList<>(itemService.getItems(Item.class));
        formController.setButtonText("item");
        itemTable.setItems(filteredData);
        onItemEdited();
        try {
            setSelectionMode();
            searchQueryListener();
            manageItemButtonListener();
            clearNotification();
            initItemFormController();
        } catch (FXMLLoaderError e) {
            e.printStackTrace();
        }
    }

    private void initItemFormController() throws FXMLLoaderError {
        loadScene("manage-item-form.fxml", itemDialogFormController);
    }

    // handle button clicks: delete, edit , add
    private void manageItemButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            // handle delete button clicks
            if (Objects.equals(newValue, formController.getDeleteButton().getText())) {
                // check if any item is selected
                if (selectedItems.size() == 1) {
                    // delete an item
                    itemService.deleteItem(selectedItems.get(0).getItemCode(), Item.class);
                } else {
                    // show error message
                    notificationController.setNotificationText("No item selected, select an item to delete", NotificationType.ERROR);
                }
                // handle edit button clicks
            } else if (Objects.equals(newValue, formController.getEditButton().getText())) {
                // check if an item is selected
                if (selectedItems.size() == 1) {
                    // set button text on the dialog form
                    itemDialogFormController.setAddEditButtonText(ButtonText.EDIT_ITEM);
                    // show item dialog form
                    dialogFromEditItem(selectedItems.get(0));
                } else {
                    // show error message
                    notificationController.setNotificationText("No item selected, select an item to edit", NotificationType.ERROR);
                }
                // handle add button clicks
            } else if (Objects.equals(newValue, formController.getAddButton().getText())) {
                notificationController.clearNotificationText();
                // set button text on the dialog form
                itemDialogFormController.setAddEditButtonText(ButtonText.ADD_ITEM);
                // show dialog form
                dialogFormAddItem();
            }
        }));
    }

    // listen to search queries
    private void searchQueryListener() {
        searchController.getSearchQuery().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue)));
    }

    // set selection mode
    private void setSelectionMode() {
        selectionModel = itemTable.getSelectionModel();
        selectedItems = selectionModel.getSelectedItems();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    private void clearNotification() {
        selectedItems.addListener((ListChangeListener<Item>) change ->
                notificationController.clearNotificationText());
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

    // fill dialog form with selected item to edit
    private void dialogFromEditItem(Item item) {
        itemDialogFormController.editItem(item);
    }

    private void dialogFormAddItem() {
        clearTableSelection();
        itemDialogFormController.addItem();
    }

    private void onItemEdited() {
        itemDialogFormController.itemEditedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (Boolean.TRUE.equals(t1)) {
                onEditCompleted(itemTable);
            }
        });
    }
}

