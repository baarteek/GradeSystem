package com.gradingsystem.controllers;

import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.css.converter.StringConverter;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.ChoiceBox;

public class StudentProfilesController {
    @FXML
    private Label userNameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private TableView studentDetailsTableView;
    @FXML
    private ChoiceBox filedsChoiceBox;
    @FXML
    private TextField serchedValueTextField;
    @FXML
    private TableView serchedStudentsTableView;
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("USER DATA");
            alert.setContentText("Failed to fetch user data");
            alert.showAndWait();
        }
        fillTableWithStudentData();
        filedsChoiceBox.getItems().addAll("First Name", "Last Name", "Pesel", "E-mail", "Phone Number", "Class");
        initializeTableView(studentDetailsTableView);
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    private void fillTableWithStudentData() {
        String studentsData = UserDataProvider.getAllFieldsFromTable("uczen");
        String[] students = studentsData.split("\\|");

        fillTableView(students, studentDetailsTableView);
    }

    private void fillTableView(String[] students, TableView tableView) {
        tableView.getItems().clear();
        for (String student : students) {
            String[] fields = student.split(",");
            if (fields.length >= 6) {
                ObservableList<SimpleStringProperty> row = FXCollections.observableArrayList();
                row.add(new SimpleStringProperty(fields[0]));  // ID
                row.add(new SimpleStringProperty(fields[2]));  // First Name
                row.add(new SimpleStringProperty(fields[3]));  // Last Name
                row.add(new SimpleStringProperty(fields[4]));  // Pesel
                row.add(new SimpleStringProperty(fields[5]));  // Email
                row.add(new SimpleStringProperty(fields[6]));  // Phone Number
                tableView.getItems().add(row);
            }
        }
    }

    private void initializeTableView(TableView tableView) {
        String[] columnNames = {"ID", "First Name", "Last Name", "Pesel", "Email", "Phone Number"};
        for (int i = 0; i < columnNames.length; i++) {
            final int finalIdx = i;
            TableColumn<ObservableList<SimpleStringProperty>, String> column = new TableColumn<>(columnNames[i]);
            column.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().get(finalIdx).get()));
            column.setResizable(true);
            tableView.getColumns().add(column);
        }
    }

    public void searchButtonClick() {
        if(checkFieldsNotEmpty()) {
            String studentsData = UserDataProvider.getStudentByField(filedsChoiceBox.getValue().toString(), serchedValueTextField.getText());
            if(studentsData.equals("GET_STUDENT_FAILURE")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("No results found");
                alert.showAndWait();
            } else {
                String[] students = studentsData.split("\\|");
                serchedStudentsTableView.getColumns().clear();
                initializeTableView(serchedStudentsTableView);
                fillTableView(students, serchedStudentsTableView);
            }
        }
    }


    private boolean checkFieldsNotEmpty() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        if(filedsChoiceBox.getValue() == null) {
            alert.setContentText("Select the field by which you want to search for a student");
            alert.showAndWait();
            return false;
        }
        if(serchedValueTextField.getText().isEmpty()) {
            alert.setContentText("Enter the value by which the student is to be searched");
            alert.showAndWait();
            return false;
        }
        return true;
    }

    public void logout(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);
    }

    public void gradeManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "grade-management-view", User.getCssFileName(), this);
    }

    public void gradeOverviewClick() {
    }

    public void statisticsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-statistics-view", User.getCssFileName(), this);
    }

    public void studentProfilesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-profiles-view", User.getCssFileName(), this);
    }

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "class-management-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "subject-management-view", User.getCssFileName(), this);
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", User.getCssFileName(), this);
    }

}
