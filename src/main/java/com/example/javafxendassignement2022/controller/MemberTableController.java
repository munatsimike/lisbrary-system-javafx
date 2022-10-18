package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.database.ItemMemberDatabase;
import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.model.AbstractConvertCellFactory;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.enums.NotificationType;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @FXML
    TableColumn<Member, LocalDate> dateOfBirthColumn;

    private AddEditMemberFormController addEditMemberController;
    private FXMLLoader loader;

    public MemberTableController(ItemMemberDatabase memberDatabase) {
        this.memberDatabase = memberDatabase;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredData = new FilteredList<>(memberDatabase.getRecords(Member.class));
        membersTable.setItems(filteredData);
        searchController.setPromptText("firstname, lastname");
        formController.setButtonText("member");
        initMemberFormController();
        formButtonListener();
        searchQueryListener();
        setSelectionMode();
        clearTableSelection();
        formatDate();
        refreshTable();
    }

    private void initMemberFormController() {
        addEditMemberController = new AddEditMemberFormController(memberDatabase);
        loadScene("add-edit-member-form.fxml", addEditMemberController);
    }

    private void formatDate() {
        dateOfBirthColumn.setCellFactory((AbstractConvertCellFactory<Member, LocalDate>)
                value -> DateTimeFormatter.ofPattern("dd MMMM yyyy").format(value));
    }

    private void formButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.deleteButton.getText())) {
                if (selectedMembers.size() == 1) {
                    memberDatabase.deleteRecord(selectedMembers.get(0).getIdentifier(), Member.class);
                } else {
                    notificationController.setNotificationText("No member selected, select a member to delete", NotificationType.Error);
                }
            } else if (Objects.equals(newValue, formController.editButton.getText())) {
                try {
                    if (selectedMembers.size() == 1) {
                        addEditMemberController.setAddEditMemberController(ButtonText.EDIT_MEMBER);
                        editMember(selectedMembers.get(0));
                    } else {
                        notificationController.setNotificationText("No member selected, select a member to edit", NotificationType.Error);
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

    private void refreshTable(){
        addEditMemberController.operationCompleted.addListener((observableValue, aBoolean, t1) -> {
            if(t1) {
                membersTable.refresh();
            }
        });
    }
}
