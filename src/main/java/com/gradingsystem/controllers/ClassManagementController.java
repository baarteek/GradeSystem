package com.gradingsystem.controllers;

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
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class ClassManagementController {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TextField classNameTextField;
    @FXML
    private ListView studentsListView;
    @FXML
    private ListView classesList;
    @FXML
    private ListView studentsToAddList;
    @FXML
    private ListView classToAddList;
    @FXML
    private ListView classToRemoveList;
    @FXML
    private ListView studentsToRemoveList;
    @FXML
    private ListView classToRenameList;
    @FXML
    private TextField renameTestField;
    @FXML
    private Button loadStudentsButton;
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
        String[] userData = UserDataProvider.getUserData("nauczyciel", LoginController.userID);
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

        studentsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsToAddList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        studentsToRemoveList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    public void removeClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        ObservableList<String> selectedItems = classesList.getSelectionModel().getSelectedItems();
        if(selectedItems.isEmpty()) {
            alert.setContentText("Select the class to be removed");
            alert.showAndWait();
        } else {
            String data = selectedItems.toString();
            data = data.replace("[", "").replace("]", "");
            String classID = getIDFromString(data);
            UserDataProvider.updateField("uczen", "klasa_id", "0", "klasa_id", classID);
            if(UserDataProvider.deleteRecordById("klasa", classID, "klasa_id")) {
                alert.setContentText("Class removed");
                alert.showAndWait();
            } else {
                alert.setContentText("Class could not be deleted");
                alert.showAndWait();
            }
        }
    }

    public void renameClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        String newName = renameTestField.getText();
        if(newName.isEmpty()) {
            alert.setContentText("Enter your new name");
            alert.showAndWait();
        } else {
            newName.toUpperCase();
            ObservableList<String> selectedItems = classToRenameList.getSelectionModel().getSelectedItems();
            if(selectedItems.isEmpty()) {
                alert.setContentText("Select the class you want to rename");
                alert.showAndWait();
            } else {
                String data = selectedItems.toString();
                data = data.replace("[", "").replace("]", "");
                String classID = getIDFromString(data);
                if(UserDataProvider.isDataInDatabase("klasa", "nazwa", newName)) {
                    alert.setContentText("There is a class with that name");
                    alert.showAndWait();
                } else {
                    if(UserDataProvider.updateField("klasa", "nazwa", newName, "klasa_id", classID)) {
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

    public void loadClasses(ListView listView) {
        ObservableList<String> listItems = UserDataProvider.getAllFieldsFromClass("klasa");
        if(listItems == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data");
            alert.showAndWait();
        }else {
            listView.setItems(listItems);
        }
    }

    public void addStudentsToClassAction() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        ObservableList<String> selectedItems = classToAddList.getSelectionModel().getSelectedItems();
        ObservableList<String> selectedItemsStudents = studentsToAddList.getSelectionModel().getSelectedItems();
        if(selectedItems.isEmpty() || selectedItemsStudents.isEmpty()) {
            alert.setContentText("No data selected");
            alert.showAndWait();
        } else {
            String className = new String();
            for (String item : selectedItems) {
                String[] parts = item.split("\tName: ");
                if (parts.length == 2) {
                    className = parts[1];
                }
            }
            addStudentsToClass(studentsToAddList, className);

            alert.setContentText("Students have been added to the class");
            alert.showAndWait();
        }

    }

    public void addNewClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        String className = classNameTextField.getText();
        if(className.isEmpty()) {
            alert.setContentText("Enter the class name");
            alert.showAndWait();
        } else {
            String result = UserDataProvider.addNewClass(className.toUpperCase());
            if(result.equals("ADD_CLASS_SUCCESS")) {
                alert.setContentText("New class added");
                alert.showAndWait();

                addStudentsToClass(studentsListView, className);
            } else {
                alert.setContentText("There is a class with the given name");
                alert.showAndWait();
            }
        }
    }

    private void addStudentsToClass(ListView listView, String className) {
        ObservableList<String> selectedItems = listView.getSelectionModel().getSelectedItems();
        for (String item : selectedItems) {;
            if(!getIDFromString(item).equals("No ID found")) {
                String studentID = getIDFromString(item);
                UserDataProvider.addStudentToClass(className, studentID);
            }
        }
    }

    public static String getIDFromString(String inputString) {
        String[] attributes = inputString.split("\t");
        for (String attribute : attributes) {
            String[] keyValue = attribute.split(":");
            if (keyValue[0].trim().equals("ID")) {
                return keyValue[1].trim();
            }
        }
        return "No ID found";
    }



    public void loadStudentData(ListView listView) {
        ObservableList<String> studentsWithoutClass = UserDataProvider.getStudentsWithoutClass();
        if(studentsWithoutClass == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("There are no students with no classes assigned to them");
            alert.showAndWait();
        } else {
            listView.setItems(studentsWithoutClass);
        }
    }

    public void removeStudentsFromClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        ObservableList<String> selectedClass = classToRemoveList.getSelectionModel().getSelectedItems();
        ObservableList<String> selectedStudents = studentsToRemoveList.getSelectionModel().getSelectedItems();
        if(selectedClass.isEmpty() || selectedStudents.isEmpty()) {
            alert.setContentText("Select class and students");
            alert.showAndWait();
        } else {
            String dataClass = selectedClass.toString();
            dataClass = dataClass.replace("[", "").replace("]", "");
            String classID = getIDFromString(dataClass);
            String dataStudent = selectedStudents.toString();
            dataStudent = dataStudent.replace("[", "").replace("]", "");
            String studentID = getIDFromString(dataStudent);

            String condition = classID + " AND uczen_id=" + studentID;

            if(UserDataProvider.updateField("uczen", "klasa_id", "0", "klasa_id", condition)) {
                alert.setContentText("Students have been removed from this class");
                alert.showAndWait();
            } else {
                alert.setContentText("Could not remove students from this class");
                alert.showAndWait();
            }
        }
    }

    public void loadStudentDataAction() {
        loadStudentData(studentsListView);
    }

    public void loadStudentDataAction2() {
        loadStudentData(studentsToAddList);
    }

    public void loadClassesToRemove() {
        loadClasses(classesList);
    }

    public void loadClassesToEdit() {
        loadClasses(classToAddList);
    }

    public void loadClassesToRename() {
        loadClasses(classToRenameList);
    }

    public void loadClassesAct() {
        loadClasses(classToRemoveList);
    }

    public void loadStudentsToRemoveFromClass() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        ObservableList<String> selectedClass = classToRemoveList.getSelectionModel().getSelectedItems();
        if(selectedClass.isEmpty()) {
            alert.setContentText("Choose a class");
            alert.showAndWait();
        } else {
            String data = selectedClass.toString();
            data = data.replace("[", "").replace("]", "");
            String classID = getIDFromString(data);
            String condition = "klasa_id=" + classID;
            String result = UserDataProvider.getTableData("uczen", "uczen_id, imie, nazwisko, email, pesel, telefon", condition);
            if(result == null) {
                alert.setContentText("There are no students in this class");
                alert.showAndWait();
            } else {
                ObservableList<String> formattedData = formatDataForListView(result);
                studentsToRemoveList.setItems(formattedData);
            }
        }
    }

    private static ObservableList<String> formatDataForListView(String data) {
        ObservableList<String> formattedData = FXCollections.observableArrayList();
        String[] records = data.split("\\|");

        for (int i = 0; i < records.length; i++) {
            String[] fields = records[i].split(",");
            String formattedRecord = String.format("ID: %s \t Name: %s \t Surname: %s \t E-mail: %s \t Pesel: %s \t Phone Number: %s",
                    fields[0], fields[1], fields[2], fields[3], fields[4], fields[5]);
            formattedData.add(formattedRecord);
        }

        return formattedData;
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

    public void studentProfilesClick() {
    }

    public void subjectManagementClick() {
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", "teacher-style", this);
    }

}
