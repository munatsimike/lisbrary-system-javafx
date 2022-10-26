package com.example.javafxendassignement2022.controller;

import com.example.javafxendassignement2022.enums.ButtonText;
import com.example.javafxendassignement2022.enums.NotificationType;
import com.example.javafxendassignement2022.exception.FXMLLoaderError;
import com.example.javafxendassignement2022.model.Member;
import com.example.javafxendassignement2022.service.MemberService;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.function.Predicate;

// this class contains code to display members in a table. further this class works together with the memberDialogFormController to manager members
public class MemberTableController extends BaseController implements Initializable {
    private ObservableList<Member> selectedMembers;
    private TableView.TableViewSelectionModel<Member> selectionModel;
    private FilteredList<Member> filteredData;
    private final MemberService memberService;
    @FXML
    private TableView<Member> membersTable;
    @FXML
    TableColumn<Member, LocalDate> dateOfBirthColumn;
    private final MemberDialogFormController memberDialogFormController;

    public MemberTableController(MemberService memberService, MemberDialogFormController memberDialogFormController) {

        this.memberDialogFormController = memberDialogFormController;
        this.memberService = memberService;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        filteredData = new FilteredList<>(memberService.getMembers(Member.class));
        membersTable.setItems(filteredData);
        searchController.setPromptText("firstname, lastname");
        formController.setButtonText("member");
        try {
            initMemberFormController();
            manageMemberButtonListener();
            searchQueryListener();
            setSelectionMode();
            clearTableSelection();
            formatDate();
            clearNotification();
            onMemberEdited();
        } catch (FXMLLoaderError e) {
            e.printStackTrace();
        }
    }

    private void initMemberFormController() throws FXMLLoaderError {
        loadScene("manage-member-form.fxml", memberDialogFormController);
    }

    //format date on the table to date , month , year
    private void formatDate() {
        dateOfBirthColumn.setCellFactory(column -> new TableCell<>() {
            private final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy");

            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty) {
                    setText(null);
                } else {
                    setText(format.format(date));
                }
            }
        });
    }

    // handle button clicks: edi, add, delete
    private void manageMemberButtonListener() {
        formController.selectedButton().addListener(((observable, oldValue, newValue) -> {
            if (Objects.equals(newValue, formController.getDeleteButton().getText())) {
                // check if any member is selected
                if (selectedMembers.size() == 1) {
                    // delete member
                    memberService.deleteMember(selectedMembers.get(0).getIdentifier(), Member.class);
                } else {
                    notificationController.setNotificationText("No member selected, select a member to delete", NotificationType.ERROR);
                }
                // edit member
            } else if (Objects.equals(newValue, formController.getEditButton().getText())) {
                // check if any member is selected
                if (selectedMembers.size() == 1) {
                    memberDialogFormController.setAddEditMemberController(ButtonText.EDIT_MEMBER);
                    // show dialogue
                    dialogFormEditMember(selectedMembers.get(0));
                } else {
                    notificationController.setNotificationText("No member selected, select a member to edit", NotificationType.ERROR);
                }
            } else if (Objects.equals(newValue, formController.getAddButton().getText())) {
                memberDialogFormController.setAddEditMemberController(ButtonText.ADD_MEMBER);
                notificationController.clearNotificationText();
                dialogFormAddMember();
            }
        }));
    }

    private void searchQueryListener() {
        searchController.getSearchQuery().addListener((observable, oldValue, newValue) ->
                filteredData.setPredicate(createPredicate(newValue)));
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

    // show dialog with member to edit
    private void dialogFormEditMember(Member member) {
        memberDialogFormController.editMember(member);
    }

    // show dialog to add member
    private void dialogFormAddMember() {
        clearTableSelection();
        memberDialogFormController.addMember();
    }

    private void clearNotification() {
        selectedMembers.addListener((ListChangeListener<Member>) change ->
                notificationController.clearNotificationText());
    }

    private void onMemberEdited() {
        memberDialogFormController.memberEditedProperty().addListener((observableValue, aBoolean, t1) -> {
            if (Boolean.TRUE.equals(t1)) {
                onEditCompleted(membersTable);
            }
        });
    }
}
