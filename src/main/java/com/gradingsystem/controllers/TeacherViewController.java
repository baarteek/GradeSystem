package com.gradingsystem.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class TeacherViewController {
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
    private TabPane mainTabPane;
    @FXML
    private RadioButton passwordRadioButton;
    @FXML
    private PasswordField newPasswordField;
    @FXML
    private PasswordField repeatNewPasswordField;
    @FXML
    private PasswordField currentPasswordField;
    @FXML
    private Button changePasswordButton;
    @FXML
    private Label newPasswordLabel;
    @FXML
    private Label repeatNewPasswordLabel;
    @FXML
    private Label currentPasswordLabel;
    @FXML
    private Label newValueMyAccountLabel;
    @FXML
    private TextField valueMyAccountTestField;
    @FXML
    private Button editMyAccountButton;
    @FXML
    private RadioButton otherFieldsRadioButton;
    @FXML
    private RadioButton nameMyAccountRadioButton;
    @FXML
    private RadioButton surnameMyAccountRadioButton;
    @FXML
    private RadioButton peselMyAccountRadioButton;
    @FXML
    private RadioButton emailMyAccountRadioButton;
    @FXML
    private RadioButton phoneNumberMyAccountRadioButton;

    private Parent root;
    private Stage loginStage;
    private Scene loginScene;

    public void switchToLoginScene(MouseEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/gradingsystem/views/login-view.fxml"));
            loginStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loginScene = new Scene(root, 1600, 800);
            loginScene.getStylesheets().add(getClass().getResource("/com/gradingsystem/css/login-style.css").toExternalForm());
            loginStage.setScene(loginScene);
            loginStage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void passwordRadioButtonClick() {
        if(passwordRadioButton.isSelected()) {
            newPasswordField.setDisable(false);
            repeatNewPasswordField.setDisable(false);
            currentPasswordField.setDisable(false);
            changePasswordButton.setDisable(false);
            newPasswordLabel.setDisable(false);
            repeatNewPasswordLabel.setDisable(false);
            currentPasswordLabel.setDisable(false);
        } else {
            newPasswordField.setDisable(true);
            repeatNewPasswordField.setDisable(true);
            currentPasswordField.setDisable(true);
            changePasswordButton.setDisable(true);
            newPasswordLabel.setDisable(true);
            repeatNewPasswordLabel.setDisable(true);
            currentPasswordLabel.setDisable(true);
        }
    }

    public void otherFieldsRadioButtonClick() {
        if(otherFieldsRadioButton.isSelected()) {
            nameMyAccountRadioButton.setDisable(false);
            surnameMyAccountRadioButton.setDisable(false);
            peselMyAccountRadioButton.setDisable(false);
            emailMyAccountRadioButton.setDisable(false);
            phoneNumberMyAccountRadioButton.setDisable(false);
            editMyAccountButton.setDisable(false);
            valueMyAccountTestField.setDisable(false);
            newValueMyAccountLabel.setDisable(false);
        } else {
            nameMyAccountRadioButton.setDisable(true);
            surnameMyAccountRadioButton.setDisable(true);
            peselMyAccountRadioButton.setDisable(true);
            emailMyAccountRadioButton.setDisable(true);
            phoneNumberMyAccountRadioButton.setDisable(true);
            editMyAccountButton.setDisable(true);
            valueMyAccountTestField.setDisable(true);
            newValueMyAccountLabel.setDisable(true);
        }
    }


    public void logout(MouseEvent event) throws IOException {
        switchToLoginScene(event);

        // mechanizm wylogowania uzytkownika
    }

    public void gradeManagementClick() {
        mainTabPane.getSelectionModel().select(0);
    }

    public void gradeOverviewClick() {
        mainTabPane.getSelectionModel().select(1);
    }

    public void statisticsClick() {
        mainTabPane.getSelectionModel().select(2);
    }

    public void studentProfilesClick() {
        mainTabPane.getSelectionModel().select(3);
    }

    public void classManagementClick() {
        mainTabPane.getSelectionModel().select(4);
    }

    public void subjectManagementClick() {
        mainTabPane.getSelectionModel().select(5);
    }

    public void notificationsClick() {
        mainTabPane.getSelectionModel().select(6);
    }

    public void messagesClick() {
        mainTabPane.getSelectionModel().select(7);
    }

    public void settingsClick() {
        mainTabPane.getSelectionModel().select(8);
    }
}
