package com.gradingsystem.controllers;

import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.DataPresenter;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class GradeOverviewController {
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
    private Separator menuSeparator7;
    @FXML
    private Separator menuSeparator8;
    @FXML
    private Separator menuSeparator3;
    @FXML
    private Separator menuSeparator4;
    @FXML
    private Separator menuSeparator6;
    @FXML
    private Separator menuSeparator5;
    @FXML
    private HBox notificationsHBox;
    @FXML
    private VBox accountMenuVBox;
    @FXML
    private ListView studentsSelect;
    @FXML
    private ListView studentGrades;
    @FXML
    private HBox classManagementHBox;
    @FXML
    private HBox subjectManagementHBox;
    @FXML
    private HBox studentProfilesHBox;
    @FXML
    private HBox messagesHBox;

    private Parent root;
    private Stage stage;
    private Scene scene;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String password;
    private String id;

    public void initialize() {
        ViewSwitcher.switchMenuIcons(this, gradeManagementImage, gradeOverviewImage, statisticsImage, studentProfilesImage, classManagementImage, subjectManagementImage, notificationsImage, messagesImage, settingsImage, logoutImage, emailImageView,  profileImageView, settingsImageView);
        String[] userData = new String[10];
        if (User.getType() == "teacher") {
            userData = UserDataProvider.getUserData("nauczyciel", LoginController.userID);
            accountMenuVBox.getChildren().remove(classManagementHBox);
            accountMenuVBox.getChildren().remove(studentProfilesHBox);
            accountMenuVBox.getChildren().remove(subjectManagementHBox);
            accountMenuVBox.getChildren().remove(messagesHBox);
            accountMenuVBox.getChildren().remove(menuSeparator5);
            accountMenuVBox.getChildren().remove(menuSeparator6);
            accountMenuVBox.getChildren().remove(menuSeparator4);
            accountMenuVBox.getChildren().remove(menuSeparator7);
            accountMenuVBox.getChildren().remove(menuSeparator8);
            accountMenuVBox.getChildren().remove(notificationsHBox);
        } else if (User.getType() == "admin") {
            userData = UserDataProvider.getUserData("admin", LoginController.userID);
        }
        if(!userData[0].equals("GET_USER_DATA_FAILURE")) {
            id = userData[1];
            System.out.println(id);
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
        accountMenuVBox.getChildren().remove(menuSeparator7);
        accountMenuVBox.getChildren().remove(menuSeparator8);
        accountMenuVBox.getChildren().remove(notificationsHBox);


        studentsSelect.setOnMouseClicked(event -> {
            String selectedStudent = (String) studentsSelect.getSelectionModel().getSelectedItem();
            loadStudentGrades(selectedStudent);
        });
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    public void loadStudents() {
        String[] students;
        if (User.getType() == "admin") {
            students = UserDataProvider.getAllStudents();
        }
        else {
            students = UserDataProvider.getAllStudentsFromTeacherGroups(Integer.parseInt(id));
        }

        for (int i = 1; i < students.length; i++) {
            String[] parts = students[i].split(" ");
            String formattedStudent = parts[0] + " " + parts[1] + " " + parts[2];
            studentsSelect.getItems().add(formattedStudent);
        }
    }

    private void loadStudentGrades(String selectedStudent) {
        ObservableList<String> items = studentGrades.getItems();
        items.clear();

        String[] partsStudent = selectedStudent.split(" ");
        String[] grades = UserDataProvider.getStudentSubjectsAndGrades(Integer.parseInt(partsStudent[0]));
        Map<String, Map<String, List<Integer>>>  yearMap = StudentGradesController.extractSubjectGrades(grades);
        displaySubjectGrades(yearMap);
    }

    private void displaySubjectGrades(Map<String, Map<String, List<Integer>>> yearMap) {
        for (Map.Entry<String, Map<String, List<Integer>>> yearEntry : yearMap.entrySet()) {
            String year = yearEntry.getKey();
            Map<String, List<Integer>> subjectMap = yearEntry.getValue();

            studentGrades.getItems().add("Rok " + year);

            for (Map.Entry<String, List<Integer>> subjectEntry : subjectMap.entrySet()) {
                String subject = subjectEntry.getKey();
                List<Integer> grades = subjectEntry.getValue();

                StringBuilder sb = new StringBuilder();

                sb.append(subject).append(": ");

                if (grades != null) {
                    for (int i = 0; i < grades.size(); i++) {
                        sb.append(grades.get(i));

                        if (i < grades.size() - 1) {
                            sb.append(", ");
                        }
                    }
                }

                studentGrades.getItems().add(sb.toString());
            }

            studentGrades.getItems().add("");
        }
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
        ViewSwitcher.switchScene(event, root, stage, scene, "statistics-view", User.getCssFileName(), this);
    }

    public void studentProfilesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-profiles-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "subject-management-view", User.getCssFileName(), this);
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "register-view", User.getCssFileName(), this);
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", User.getCssFileName(), this);
    }

}
