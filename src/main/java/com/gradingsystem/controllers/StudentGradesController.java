package com.gradingsystem.controllers;

import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StudentGradesController {
    @FXML
    private AnchorPane mainAnchorPane;
    @FXML
    private AnchorPane sideAnchorePane;
    @FXML
    private Label userNameLabel;
    @FXML
    private ImageView settingsImageView;
    @FXML
    private Label emailLabel;
    @FXML
    private HBox gradeManagementHBox;
    @FXML
    private HBox gradeOverviewHBox;
    @FXML
    private HBox statisticsHBox;
    @FXML
    private HBox studentProfilesHBox;
    @FXML
    private HBox classManagementHBox;
    @FXML
    private HBox subjectManagementHBox;
    @FXML
    private HBox notificationsHBox;
    @FXML
    private HBox messagesHBox;
    @FXML
    private HBox settingsHBox;
    @FXML
    private HBox logoutHBox;
    @FXML
    private HBox classesMyAccountHBox;
    @FXML
    private HBox subjectsMyAccountHBox;
    @FXML
    private Separator menuSeparator2;
    @FXML
    private Separator menuSeparator4;
    @FXML
    private Separator menuSeparator5;
    @FXML
    private Separator menuSeparator6;
    @FXML
    private VBox accountMenuVBox;
    @FXML
    private ListView subjectsList;
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


    private Parent root;
    private Stage loginStage;
    private Scene loginScene;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;

    public void initialize() {
        ViewSwitcher.switchMenuIcons(this, gradeManagementImage, gradeOverviewImage, statisticsImage, studentProfilesImage, classManagementImage, subjectManagementImage, notificationsImage, messagesImage, settingsImage, logoutImage, emailImageView,  profileImageView, settingsImageView);
        String[] userData = UserDataProvider.getUserData("uczen", LoginController.userID);
        String[] gradesData = UserDataProvider.getStudentSubjectsAndGrades();

        if(!userData[0].equals("GET_USER_DATA_FAILURE")) {
            name = userData[3];
            surname = userData[4];
            pesel = userData[5];
            email = userData[6];
            phoneNumber = userData[7];
            updateUserFields();

            accountMenuVBox.getChildren().remove(classesMyAccountHBox);
            accountMenuVBox.getChildren().remove(subjectsMyAccountHBox);
            accountMenuVBox.getChildren().remove(gradeManagementHBox);
            accountMenuVBox.getChildren().remove(menuSeparator2);
            accountMenuVBox.getChildren().remove(studentProfilesHBox);
            accountMenuVBox.getChildren().remove(menuSeparator4);
            accountMenuVBox.getChildren().remove(classManagementHBox);
            accountMenuVBox.getChildren().remove(subjectManagementHBox);
            accountMenuVBox.getChildren().remove(menuSeparator5);
            accountMenuVBox.getChildren().remove(menuSeparator6);

            accountMenuVBox.requestLayout();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("USER DATA");
            alert.setContentText("Failed to fetch user data");
            alert.showAndWait();
        }

        if (!gradesData[0].equals("GET_STUDENT_SUBJECTS_DATA_FAILURE")) {
            renderGrades(gradesData);
        }
    }

    private void renderGrades(String[] gradesData) {
        Map<String, Map<String, List<Integer>>>  yearMap = extractSubjectGrades(gradesData);
        displaySubjectGrades(yearMap);
    }

    private Map<String, Map<String, List<Integer>>> extractSubjectGrades(String[] gradesData) {
        Map<String, Map<String, List<Integer>>> yearMap = new HashMap<>();

        for (int i = 1; i < gradesData.length; i++) {
            int startIndex = gradesData[i].indexOf("(") + 1;
            int endIndex = gradesData[i].indexOf(")");
            String year = gradesData[i].substring(startIndex, endIndex);

            if (!yearMap.containsKey(year)) {
                yearMap.put(year, new HashMap<>());
            }

            Map<String, List<Integer>> subjectMap = yearMap.get(year);

            String gradeString = gradesData[i].substring(gradesData[i].length() - 1);

            if (!Character.isDigit(gradeString.charAt(0))) {
                String subject = gradesData[i].substring(0, startIndex - 2);
                subjectMap.put(subject, null);
            }
            else {
                String subject = gradesData[i].substring(0, startIndex - 2);
                int grade = Integer.parseInt(gradeString);

                if (subjectMap.containsKey(subject)) {
                    List<Integer> grades = subjectMap.get(subject);
                    grades.add(grade);
                } else {
                    List<Integer> grades = new ArrayList<>();
                    grades.add(grade);
                    subjectMap.put(subject, grades);
                }
            }

            yearMap.put(year, subjectMap);
        }

        return yearMap;
    }

    private void displaySubjectGrades(Map<String, Map<String, List<Integer>>> yearMap) {
        for (Map.Entry<String, Map<String, List<Integer>>> yearEntry : yearMap.entrySet()) {
            String year = yearEntry.getKey();
            Map<String, List<Integer>> subjectMap = yearEntry.getValue();

            subjectsList.getItems().add("Rok " + year);

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

                subjectsList.getItems().add(sb.toString());
            }

            subjectsList.getItems().add("");
        }
    }

    public void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    public void logout(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, loginStage, loginScene, "login-view", "login-style", this);
        LoginController.userID = -1;
    }

    public void gradeManagementClick() {
        System.out.println(LoginController.userID);
    }

    public void gradeOverviewClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, loginStage, loginScene, "student-grades-view", User.getCssFileName(), this);
    }

    public void statisticsClick() {
    }

    public void studentProfilesClick() {
    }

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, loginStage, loginScene, "class-management-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, loginStage, loginScene, "subject-management-view", User.getCssFileName(), this);
    }

    public void notificationsClick() {
    }

    public void messagesClick() {
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, loginStage, loginScene, "myaccount-view", User.getCssFileName(), this);
    }
}
