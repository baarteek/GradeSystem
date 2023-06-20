package com.gradingsystem.controllers;

import com.gradingsystem.server.Database;
import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Pair;

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
    @FXML
    private VBox accountMenuVBox;
    @FXML
    private HBox classManagementHBox;
    @FXML
    private HBox subjectManagementHBox;
    @FXML
    private HBox studentProfilesHBox;
    @FXML
    private HBox messagesHBox;
    @FXML
    private Separator menuSeparator3;
    @FXML
    private Separator menuSeparator4;
    @FXML
    private Separator menuSeparator5;
    @FXML
    private Separator menuSeparator7;
    @FXML
    private Separator menuSeparator8;
    @FXML
    private HBox notificationsHBox;

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
            accountMenuVBox.getChildren().remove(classManagementHBox);
            accountMenuVBox.getChildren().remove(subjectManagementHBox);
            accountMenuVBox.getChildren().remove(studentProfilesHBox);
            accountMenuVBox.getChildren().remove(messagesHBox);
            accountMenuVBox.getChildren().remove(menuSeparator3);
            accountMenuVBox.getChildren().remove(menuSeparator4);
            accountMenuVBox.getChildren().remove(menuSeparator5);
            accountMenuVBox.getChildren().remove(menuSeparator7);
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

        accountMenuVBox.getChildren().remove(menuSeparator7);
        accountMenuVBox.getChildren().remove(menuSeparator8);
        accountMenuVBox.getChildren().remove(notificationsHBox);

        addOptionsToSubjectChoiceBox(selectSubject1ChoiceBox);
        addOptionsToSubjectChoiceBox(selectSubject2ChoiceBox);
        addOptionsToSubjectChoiceBox(selectSubject3ChoiceBox);
        sortChoiceBox.getItems().addAll("Class", "Subject", "Student");
        sortChoiceBox2.getItems().addAll("Class", "Subject", "Student");
        sortChoiceBox3.getItems().addAll("Class", "Subject", "Student");
        addGradeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        editGradeTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        removeGradesTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    private void addOptionsToSubjectChoiceBox(ChoiceBox choiceBox) {
            String subjects = UserDataProvider.getAllFieldsFromTable("przedmiot");
            String[] entries = subjects.split("\\|");
            List<String> names = new ArrayList<>();

            for (int i = 2; i < entries.length; i++) {
                String name = entries[i].split(",")[1];
                names.add(name);
            }

            choiceBox.setItems(FXCollections.observableArrayList(names));
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
        String[] columnNames = {"Class", "Class ID", "Student ID", "First Name", "Last Name", "Subject", "Subject ID"};
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

    public void setAddGradeButton() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);

        ObservableList<Map<String, String>> selectedItems = addGradeTableView.getSelectionModel().getSelectedItems();
        if(selectedItems.isEmpty()) {
            alert.setContentText("Select students from the table");
            alert.showAndWait();
        } else{
            Pair<Integer, Double> gradAndWeight = addGradeModalWindow();
            if(gradAndWeight == null) {
                alert.setContentText("Grade not added");
                alert.showAndWait();
            } else {
                boolean isAdded = true;
                for(Map item: selectedItems) {
                    String studentID = (String) item.get("Student ID");
                    String subjectID = (String) item.get("Subject ID");
                    String grade = String.valueOf(gradAndWeight.getKey());
                    String weight = String.valueOf(gradAndWeight.getValue());
                    if(!UserDataProvider.addGrade(studentID, subjectID, grade, weight)) {
                        isAdded = false;
                    }
                }
                if(isAdded) {
                    alert.setContentText("Added new grades");
                    alert.showAndWait();
                } else {
                    alert.setContentText("Grade not added");
                    alert.showAndWait();
                }
            }
        }
    }

    private Pair<Integer, Double> addGradeModalWindow() {
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Add Grade");

        dialogStage.setMinWidth(300);
        dialogStage.setMinHeight(200);

        Label label = new Label("Add a grade");
        ChoiceBox<Integer> choiceBox = new ChoiceBox<>(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6));
        choiceBox.setValue(6);
        TextField weightField = new TextField();
        weightField.setAlignment(Pos.CENTER);
        weightField.setPromptText("Enter weight value");

        final Pair<Integer, Double>[] result = new Pair[1];
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            try {
                double weight = Double.parseDouble(weightField.getText());
                int grade = choiceBox.getValue();
                result[0] = new Pair<>(grade, weight);
                dialogStage.close();
            } catch (NumberFormatException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Input Error");
                alert.setContentText("Please enter a valid number for weight!");
                alert.showAndWait();
            }
        });

        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, choiceBox, weightField, okButton);
        layout.setAlignment(Pos.CENTER);

        Scene dialogScene = new Scene(layout);
        dialogStage.setScene(dialogScene);
        dialogStage.showAndWait();

        return result[0];
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

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "class-management-view", User.getCssFileName(), this);
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
