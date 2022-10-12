package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.ItemDataBase;
import com.example.javafxendassignement2022.model.Item;
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

public class ItemController  extends BaseController implements Initializable {

   private ObservableList<Item> selectedItems;
   private TableView.TableViewSelectionModel<Item> selectionModel;
   private final FilteredList<Item> filteredData;
   private final ItemDataBase table;

    @FXML
    private TableView<Item> itemsTable;
    @FXML
    private SearchController searchController;
    @FXML
    private FormController formController;
    private AddEditItemController addEditFromController;
    private final FXMLLoader loader;


    public ItemController() {
        table = new ItemDataBase();
        filteredData = new FilteredList<>(table.getItems());
        loader = new FXMLLoader();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure the TableView
        initItemFormController();
        setSelectionMode();
        searchQueryListener();
        formButtonListener();
    }

    private void initItemFormController() {
        itemsTable.setItems(filteredData);
        loader.setLocation(LibrarySystem.class.getResource("add-edit-item-form.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEditFromController = loader.getController();
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.deleteButton.getText()) && selectedItems.size() == 1) {
                table.deleteItem(selectedItems.get(0).getItemCode());
            } else if (Objects.equals(newValue, formController.editButton.getText()) && selectedItems.size() == 1) {
                try {
                    editItem(selectedItems.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Objects.equals(newValue, formController.addButton.getText())) {
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
        selectionModel = itemsTable.getSelectionModel();
        selectedItems = selectionModel.getSelectedItems();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
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
