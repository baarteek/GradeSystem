package com.gradingsystem.controllers;

import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class GradeManagementController {
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
    private ChoiceBox sortChoiceBox;
    @FXML
    private ChoiceBox sortChoiceBox2;
    @FXML
    private ChoiceBox sortChoiceBox3;
    @FXML
    private Button loadStudentsButton;
    @FXML
    private ImageView profileImageView;
    @FXML
    private ImageView gradeManagementImage;
    @FXML
    private ImageView emailImageView;
    @FXML
    private ImageView gradeOverviewImage;
    @FXML
    private ImageView statisticsImage;
    @FXML
    private ImageView studentProfilesImage;
    @FXML
    private ImageView classManagementImage;
    @FXML
    private ImageView subjectManagementImage;
    @FXML
    private ImageView notificationsImage;
    @FXML
    private ImageView messagesImage;
    @FXML
    private ImageView settingsImage;
    @FXML
    private ImageView logoutImage;
    @FXML
    private ImageView settingsImageView;
    @FXML
    private ChoiceBox selectSubject1ChoiceBox;
    @FXML
    private ChoiceBox selectSubject2ChoiceBox;
    @FXML
    private ChoiceBox selectSubject3ChoiceBox;
    @FXML
    private TableView addGradeTableView;
    @FXML
    private TableView editGradeTableView;
    @FXML
    private TableView removeGradesTableView;
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
        ViewSwitcher.switchMenuIcons(this, gradeManagementImage, gradeOverviewImage, statisticsImage, studentProfilesImage, classManagementImage, subjectManagementImage, notificationsImage, messagesImage, settingsImage, logoutImage, emailImageView,  profileImageView, settingsImageView);
        String[] userData = new String[10];
        if (User.getType() == "teacher") {
            userData = UserDataProvider.getUserData("nauczyciel", LoginController.userID);
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
        }
        else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("USER DATA");
            alert.setContentText("Failed to fetch user data");
            alert.showAndWait();
        }
        addOptionsToSubjectChoiceBox(selectSubject1ChoiceBox);
        addOptionsToSubjectChoiceBox(selectSubject2ChoiceBox);
        addOptionsToSubjectChoiceBox(selectSubject3ChoiceBox);
        sortChoiceBox.getItems().addAll("Class", "Subject", "Student");
        sortChoiceBox2.getItems().addAll("Class", "Subject", "Student");
        sortChoiceBox3.getItems().addAll("Class", "Subject", "Student");
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    private void addOptionsToSubjectChoiceBox(ChoiceBox choiceBox) {
        if(User.getType().equals("admin")) {
            String subjects = UserDataProvider.getAllFieldsFromTable("przedmiot");
            String[] entries = subjects.split("\\|");
            List<String> names = new ArrayList<>();

            for (int i = 2; i < entries.length; i++) {
                String name = entries[i].split(",")[1];
                names.add(name);
            }

            choiceBox.setItems(FXCollections.observableArrayList(names));
        }
    }

    public void setLoadStudentsButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        if(selectSubject1ChoiceBox.getValue() == null) {
            alert.setContentText("Select subject");
            alert.showAndWait();
        } else {
            String subjectName = selectSubject1ChoiceBox.getValue().toString();
            String sorttype = "STUDENT";
            if(sortChoiceBox.getValue() != null) {
                sorttype = sortChoiceBox.getValue().toString();
            }
            String data = UserDataProvider.getStudentSorted(sorttype, subjectName);
            if(data.equals("GET_STUDENT_SORTED_FAILURE"))  {
                alert.setContentText("No data");
                alert.showAndWait();
            }else {
                addRecordToTable(addGradeTableView, data);
            }
        }
    }

    public void setLoadStudentsButton3() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        if(selectSubject3ChoiceBox.getValue() == null) {
            alert.setContentText("Select subject");
            alert.showAndWait();
        } else {
            String subjectName = selectSubject3ChoiceBox.getValue().toString();
            String sorttype = "STUDENT";
            if(sortChoiceBox3.getValue() != null) {
                sorttype = sortChoiceBox3.getValue().toString();
            }
            String data = UserDataProvider.getStudentSorted(sorttype, subjectName);
            if(data.equals("GET_STUDENT_SORTED_FAILURE"))  {
                alert.setContentText("No data");
                alert.showAndWait();
            }else {
                addRecordToTable(removeGradesTableView, data);
            }
        }
    }

    public void setLoadStudentsButton2() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        if(selectSubject2ChoiceBox.getValue() == null) {
            alert.setContentText("Select subject");
            alert.showAndWait();
        } else {
            String subjectName = selectSubject2ChoiceBox.getValue().toString();
            String sorttype = "STUDENT";
            if(sortChoiceBox2.getValue() != null) {
                sorttype = sortChoiceBox2.getValue().toString();
            }
            String data = UserDataProvider.getStudentSorted(sorttype, subjectName);
            if(data.equals("GET_STUDENT_SORTED_FAILURE"))  {
                alert.setContentText("No data");
                alert.showAndWait();
            }else {
                addRecordToTable(editGradeTableView, data);
            }
        }
    }

    private void addRecordToTable(TableView tableView, String data) {
        tableView.getColumns().clear();
        List<String> dataList = Arrays.asList(data.split("\\|"));
        System.out.println(dataList);
        String[] columnNames = {"Klasa", "Klasa ID", "Uczeń ID", "Imię", "Nazwisko", "Przedmiot", "Przedmiot ID"};
        for (String columnName : columnNames) {
            TableColumn<Map, String> column = new TableColumn<>(columnName);
            column.setCellValueFactory(new MapValueFactory(columnName));
            tableView.getColumns().add(column);
        }
        ObservableList<Map<String, String>> items = FXCollections.observableArrayList();
        for (String line : dataList) {
            String[] splitData = line.split(", ");
            Map<String, String> row = new HashMap<>();
            for (int i = 0; i < columnNames.length; i++) {
                row.put(columnNames[i], splitData[i]);
            }
            items.add(row);
        }
        tableView.setItems(items);
    }

    public void logout(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);
    }

    public void gradeManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "grade-management-view", User.getCssFileName(), this);
    }

    public void gradeOverviewClick() {
    }

    public void statisticsClick() {
    }

    public void studentProfilesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-profiles-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "subject-management-view", User.getCssFileName(), this);
    }

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "class-management-view", User.getCssFileName(), this);
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", User.getCssFileName(), this);
    }

}
