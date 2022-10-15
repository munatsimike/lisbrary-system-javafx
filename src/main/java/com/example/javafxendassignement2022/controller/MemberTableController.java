package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.Member;
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

public class MemberTableController extends BaseController implements Initializable {
    private ObservableList<Member> selectedMembers;
    private TableView.TableViewSelectionModel<Member> selectionModel;
    private FilteredList<Member> filteredData;
    private final ItemMemberDatabase memberDatabase;

    @FXML
    private TableView<Member> membersTable;
    @FXML
    private SearchController searchController;
    @FXML
    private FormController formController;
    @FXML
    private NotificationController notificationController;

    private AddEditMemberFormController addEditMemberController;
    private FXMLLoader loader;

    public MemberTableController(ItemMemberDatabase memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredData = new FilteredList<>(memberDatabase.getMembers());
        membersTable.setItems(filteredData);
        searchController.setPromptText("firstname, lastname");
        initMemberFormController();
        formButtonListener();
        searchQueryListener();
        setSelectionMode();
        clearTableSelection();
    }

    private void initMemberFormController() {
        addEditMemberController = new AddEditMemberFormController(memberDatabase);
        loadScene("add-edit-member-form.fxml", addEditMemberController);
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.deleteButton.getText())) {
                if (selectedMembers.size() == 1) {
                    memberDatabase.deleteMember(selectedMembers.get(0).getIdentifier());
                } else {
                    notificationController.setNotificationText("No member selected, select a member to delete", MessageType.Error);
                }
            } else if (Objects.equals(newValue, formController.editButton.getText())) {
                try {
                    if (selectedMembers.size() == 1) {
                        addEditMemberController.setAddEditMemberController(ButtonText.EDIT_MEMBER);
                        editMember(selectedMembers.get(0));

                    } else {
                        notificationController.setNotificationText("No member selected, select a member to edit", MessageType.Error);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (Objects.equals(newValue, formController.addButton.getText())) {
                addEditMemberController.setAddEditMemberController(ButtonText.ADD_MEMBER);
                notificationController.clearNotificationText();
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

    private void clearNotification() {
        selectedMembers.addListener((ListChangeListener<Member>) change -> {
            notificationController.clearNotificationText();
        });
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
