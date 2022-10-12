package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.LibrarySystem;
import com.example.javafxendassignement2022.database.MemberDatabase;
import com.example.javafxendassignement2022.model.Member;
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

public class MemberController extends BaseController implements Initializable {
    private ObservableList<Member> selectedMembers;
    private TableView.TableViewSelectionModel<Member> selectionModel;
    private final FilteredList<Member> filteredData;
    private final MemberDatabase membersDatabase;

    @FXML
    private TableView<Member> membersTable;
    @FXML
    private SearchController searchController;
    @FXML
    private FormController formController;
    private AddEditMemberController addEditMemberController;
    private final FXMLLoader loader;

    public MemberController() {
        membersDatabase = new MemberDatabase();
        filteredData = new FilteredList<>(membersDatabase.getMembers());
        loader = new FXMLLoader();
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Configure the TableView
        membersTable.setItems(filteredData);
        searchController.setPromptText("firstname, lastname");
        setSelectionMode();
        initMemberFormController();
        clearTableSelection();
        searchQueryListener();
        formButtonListener();
    }

    private void initMemberFormController() {
        membersTable.setItems(filteredData);
        loader.setLocation(LibrarySystem.class.getResource("add-edit-member-form.fxml"));
        try {
            loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        addEditMemberController = loader.getController();
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.deleteButton.getText()) && selectedMembers.size() == 1) {
                membersDatabase.deleteMember(selectedMembers.get(0).getIdentifier());
            } else if (Objects.equals(newValue, formController.editButton.getText()) && selectedMembers.size() == 1) {
                try {
                    editMember(selectedMembers.get(0));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Objects.equals(newValue, formController.addButton.getText())) {
                addMember();
            }
        }));
    }

    private void searchQueryListener() {
        searchController.getSearchQuery().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(createPredicate(newValue));
        });
    }

    private void setSelectionMode() {
        selectionModel = membersTable.getSelectionModel();
        selectedMembers = selectionModel.getSelectedItems();
        selectionModel.setSelectionMode(SelectionMode.SINGLE);
    }

    // clear selected row
    public void clearTableSelection() {
        selectionModel.clearSelection();
    }

    private boolean searchFindItem(Member member, String searchText) {
        return (member.getFirstName().toLowerCase().startsWith(searchText.toLowerCase())) ||
                (member.getLastName().toLowerCase().startsWith(searchText.toLowerCase()));
    }

    private Predicate<Member> createPredicate(String searchText) {
        return member -> {
            if (searchText == null || searchText.isEmpty()) return true;
            return searchFindItem(member, searchText.trim());
        };
    }

    private void editMember(Member member) throws IOException {
        clearTableSelection();
        addEditMemberController.editMember(member);
    }

    private void addMember() {
        clearTableSelection();
        addEditMemberController.addMember();
    }
}
