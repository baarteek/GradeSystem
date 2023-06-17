package com.gradingsystem.controllers;

import com.gradingsystem.server.Database;
import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.DataPresenter;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.Validator;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SubjectManagementController {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField subjectNameTextField;
    @FXML
    private TextField renameSubjectTextField;
    @FXML
    private ListView subjectsToRenameListView;
    @FXML
    private ListView subjectsToDelinkListView;
    @FXML
    private ListView classesToDelinkListView;
    @FXML
    private ListView subjectsToLinkListView;
    @FXML
    private ListView classesToLinkListView;
    @FXML
    private ListView classesListView;
    @FXML
    private ListView subjectToRemoveListView;
    @FXML
    private VBox accountMenuVBox;
    @FXML
    private HBox classManagementHBox;
    @FXML
    private HBox studentProfilesHBox;
    @FXML
    private Separator menuSeparator6;
    @FXML
    private Separator menuSeparator5;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String password;

    public void initialize() {
        String[] userData = new String[10];
        if (User.getType() == "teacher") {
            userData = UserDataProvider.getUserData("nauczyciel", LoginController.userID);
            accountMenuVBox.getChildren().remove(classManagementHBox);
            accountMenuVBox.getChildren().remove(studentProfilesHBox);
            accountMenuVBox.getChildren().remove(menuSeparator5);
            accountMenuVBox.getChildren().remove(menuSeparator6);
        } else if (User.getType() == "admin") {
            userData = UserDataProvider.getUserData("admin", LoginController.userID);
        }
        if(!userData[0].equals("GET_USER_DATA_FAILURE")) {
            name = userData[2];
            surname = userData[3];
            pesel = userData[4];
            email = userData[5];
            phoneNumber = userData[6];
            password = userData[7];
            updateUserFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("USER DATA");
            alert.setContentText("Failed to fetch user data");
            alert.showAndWait();
        }

        classesListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        classesToLinkListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        classesToDelinkListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    public void renameSubject() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        String newName = renameSubjectTextField.getText();
        if(newName.isEmpty()) {
            alert.setContentText("Enter new name");
            alert.showAndWait();
        } else {
            ObservableList<String> selectedItems = subjectsToRenameListView.getSelectionModel().getSelectedItems();
            if(selectedItems.isEmpty()) {
                alert.setContentText("Select the subject you want to rename");
                alert.showAndWait();
            } else {
                String data = selectedItems.toString();
                data = data.replace("[", "").replace("]", "");
                String subjectID = DataPresenter.getIDFromString(data);
                if(UserDataProvider.isDataInDatabase("przedmiot", "nazwa", newName)) {
                    alert.setContentText("There is a subject with that name");
                    alert.showAndWait();
                } else {
                    if(UserDataProvider.updateField("przedmiot", "nazwa", newName, "przedmiot_id", subjectID)) {
                        alert.setContentText("Name changed");
                        alert.showAndWait();
                    } else {
                        alert.setContentText("Error while renaming");
                        alert.showAndWait();
                    }
                }
            }
        }
    }

    public void removeSubject() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        ObservableList<String> selectedItems = subjectToRemoveListView.getSelectionModel().getSelectedItems();
        if(selectedItems.isEmpty()) {
            alert.setContentText("Select the subject to be removed");
            alert.showAndWait();
        } else {
            String data = selectedItems.toString();
            data = data.replace("[", "").replace("]", "");
            String subjectID = DataPresenter.getIDFromString(data);

            String condition = "przedmiot_id='" + subjectID + "'";

            if(UserDataProvider.getTableData("klasa_przedmiot", "id", condition) != null) {
                String result = UserDataProvider.getTableData("klasa_przedmiot", "id", condition);

                String[] classSubjectID = result.split("\\|");
                for(int i=0; i<classSubjectID.length; i++) {
                    UserDataProvider.deleteRecordById("klasa_przedmiot", classSubjectID[i], "id");
                }
            }

            if(UserDataProvider.deleteRecordById("przedmiot", subjectID, "przedmiot_id")) {
                alert.setContentText("Subject removed");
                alert.showAndWait();
            } else {
                alert.setContentText("Subject could not be deleted");
                alert.showAndWait();
            }
        }
    }

    public void addSubject() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        String name = subjectNameTextField.getText();
        if(name.isEmpty()) {
            alert.setContentText("Enter the subject name");
            alert.showAndWait();
        } else {
            String result = UserDataProvider.addNewSubjcet(name);
            if(result.equals("ADD_SUBJECT_SUCCESS")) {
                alert.setContentText("New subjct added");
                alert.showAndWait();

                ObservableList<String> selectedItems = classesListView.getSelectionModel().getSelectedItems();
                if(!selectedItems.isEmpty()) {
                    String condition = "nazwa='" + name + "'";
                    String subjectID = UserDataProvider.getTableData("przedmiot", "przedmiot_id", condition);
                    if(subjectID == null) {
                        alert.setContentText("Subject data could not be retrieved");
                        alert.showAndWait();
                    } else {
                        for(String item : selectedItems) {
                            String classID = DataPresenter.getIDFromString(item);
                            UserDataProvider.addLinkBetweenClassAndSubject(classID, subjectID);
                        }
                    }

                }
            } else {
                alert.setContentText("There is a subject with the given name");
                alert.showAndWait();
            }
        }
    }

    public void linkSubjectWithClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        ObservableList<String> selectedSubject = subjectsToLinkListView.getSelectionModel().getSelectedItems();
        ObservableList<String> selectedClasses = classesToLinkListView.getSelectionModel().getSelectedItems();
        if(selectedSubject.isEmpty() || selectedClasses.isEmpty()) {
            alert.setContentText("Choose subject and classes");
            alert.showAndWait();
        } else {
            String dataSubject = selectedSubject.toString();
            dataSubject = dataSubject.replace("[", "").replace("]", "");
            String subjectID = DataPresenter.getIDFromString(dataSubject);
            String result = new String();
            for(String item : selectedClasses) {
                String dataClass = item.toString();
                dataClass = dataClass.replace("[", "").replace("]", "");
                String ClassID = DataPresenter.getIDFromString(dataClass);

                result = UserDataProvider.addLinkBetweenClassAndSubject(ClassID, subjectID);
                if(result.equals("ADD_LINK_FAILURE")) {
                    break;
                }
            }
            if(result.equals("ADD_LINK_FAILURE")) {
                alert.setContentText("Failed to link subject to classs");
                alert.showAndWait();
            } else {
                alert.setContentText("Linked subject to classes");
                alert.showAndWait();
            }
        }
    }

    public void delinkSubjectWithClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        ObservableList<String> selectedSubject = subjectsToDelinkListView.getSelectionModel().getSelectedItems();
        ObservableList<String> selectedClasses = classesToDelinkListView.getSelectionModel().getSelectedItems();
        if(selectedSubject.isEmpty() || selectedClasses.isEmpty()) {
            alert.setContentText("Choose subject and classes");
            alert.showAndWait();
        } else {
            String dataSubject = selectedSubject.toString();
            dataSubject = dataSubject.replace("[", "").replace("]", "");
            String subjectID = DataPresenter.getIDFromString(dataSubject);
            boolean isDelated = true;
            for(String item : selectedClasses) {
                String dataClass = item.toString();
                dataClass = dataClass.replace("[", "").replace("]", "");
                String classID = DataPresenter.getIDFromString(dataClass);

                String condition = "klasa_id=" + classID + " AND przedmiot_id= " + subjectID;
                String removeID = UserDataProvider.getTableData("klasa_przedmiot", "id", condition);

                if(!UserDataProvider.deleteRecordById("klasa_przedmiot", removeID, "id")) {
                    isDelated = false;
                    break;
                }
            }
            if(isDelated) {
                alert.setContentText("Delinked subject to classes");
                alert.showAndWait();
            } else {
                alert.setContentText("Failed to delink subject to class");
                alert.showAndWait();
            }
        }
    }

    public void loadClassesAssociatedWithSubject() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        ObservableList<String> selectedSubject = subjectsToDelinkListView.getSelectionModel().getSelectedItems();
        if(selectedSubject.isEmpty()) {
            alert.setContentText("Choose a subject");
            alert.showAndWait();
        } else {
            String data = selectedSubject.toString();
            data = data.replace("[", "").replace("]", "");
            String subjectID = DataPresenter.getIDFromString(data);

            DataPresenter.loadClassesLinkedWithSubjectDataToListView(classesToDelinkListView, subjectID);
        }
    }

    public void loadClassesInLink() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        ObservableList<String> selectedSubject = subjectsToLinkListView.getSelectionModel().getSelectedItems();;
        if(selectedSubject.isEmpty()) {
            alert.setContentText("Choose a subject");
            alert.showAndWait();
        } else {
            String dataSubject = selectedSubject.toString();
            dataSubject = dataSubject.replace("[", "").replace("]", "");
            String subjectID = DataPresenter.getIDFromString(dataSubject);

            DataPresenter.loadClassesNotLinkedWithSubjectDataToListView(classesToLinkListView, subjectID);
        }
    }

    public void loadClassesInAddSubject() {
        DataPresenter.loadClassesDataToListView(classesListView);
    }

    public void loadSubjectsInRemove() {
        DataPresenter.loadSubjectsDataToLisView(subjectToRemoveListView);
    }

    public void loadSubjectInLink() {
        DataPresenter.loadSubjectsDataToLisView(subjectsToLinkListView);
    }

    public void loadSubjectsInDelink() {
        DataPresenter.loadSubjectsDataToLisView(subjectsToDelinkListView);
    }

    public void loadClassesInDelink() {

    }

    public void loadSubjectInRename() {
        DataPresenter.loadSubjectsDataToLisView(subjectsToRenameListView);
    }

    public void logout(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);
    }

    public void gradeManagementClick() {
    }

    public void gradeOverviewClick() {
    }

    public void statisticsClick() {
    }

    public void studentProfilesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-profiles-view", User.getCssFileName(), this);
    }

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "class-management-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick() {
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", User.getCssFileName(), this);
    }

}
