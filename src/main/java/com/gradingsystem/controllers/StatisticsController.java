package com.gradingsystem.controllers;

import com.gradingsystem.server.Database;
import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
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
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class StatisticsController {
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
    @FXML
    private TableView studentRankingTableView;

    private Parent root;
    private Stage stage;
    private Scene scene;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String password;
    private static final Logger logger = LogManager.getLogger(StatisticsController.class);

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

            logger.error("Failed to fetch user data");
        }
        setStudentRanking();

        accountMenuVBox.getChildren().remove(menuSeparator7);
        accountMenuVBox.getChildren().remove(menuSeparator8);
        accountMenuVBox.getChildren().remove(notificationsHBox);
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
    }

    private void setStudentRanking() {
        String[] studentsRanking = UserDataProvider.getStudentsRanking();
        setRankingDataInTable(studentRankingTableView, studentsRanking);
    }

    public void setRankingDataInTable(TableView<Map<String, Object>> tableView, String[] rawData) {
        TableColumn<Map<String, Object>, Integer> rankCol = new TableColumn<>("Rank");
        TableColumn<Map<String, Object>, String> idCol = new TableColumn<>("ID");
        TableColumn<Map<String, Object>, String> firstNameCol = new TableColumn<>("First Name");
        TableColumn<Map<String, Object>, String> lastNameCol = new TableColumn<>("Last Name");
        TableColumn<Map<String, Object>, String> averageCol = new TableColumn<>("Average");

        rankCol.setCellValueFactory(data -> new SimpleObjectProperty<Integer>((Integer) data.getValue().get("rank")));
        idCol.setCellValueFactory(data -> new SimpleObjectProperty<String>(data.getValue().get("id").toString()));
        firstNameCol.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue().get("firstName")));
        lastNameCol.setCellValueFactory(data -> new SimpleStringProperty((String) data.getValue().get("lastName")));
        averageCol.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().get("average").toString()));

        tableView.getColumns().add(rankCol);
        tableView.getColumns().add(idCol);
        tableView.getColumns().add(firstNameCol);
        tableView.getColumns().add(lastNameCol);
        tableView.getColumns().add(averageCol);

        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList();

        for (int i = 1; i < rawData.length; i++) {
            String[] studentData = rawData[i].split(" ");
            int id = Integer.parseInt(studentData[0]);
            String firstName = studentData[1];
            String lastName = studentData[2];
            double average = Double.parseDouble(studentData[3]);

            Map<String, Object> map = new HashMap<>();
            map.put("rank", i);
            map.put("id", id);
            map.put("firstName", firstName);
            map.put("lastName", lastName);
            map.put("average", average);

            data.add(map);
        }

        tableView.setItems(data);
    }

    public void logout(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);
    }

    public void gradeManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "grade-management-view", User.getCssFileName(), this);
    }

    public void gradeOverviewClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "grade-overview-view", User.getCssFileName(), this);
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
        ViewSwitcher.switchScene(event, root, stage, scene, "register-view", User.getCssFileName(), this);
    }

    public void settingsClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "myaccount-view", User.getCssFileName(), this);
    }

}
