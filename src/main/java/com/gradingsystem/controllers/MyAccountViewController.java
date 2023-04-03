package com.gradingsystem.controllers;

import com.gradingsystem.utils.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class MyAccountViewController {
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
    private Stage stage;
    private Scene scene;


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
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);

        //  wylogowanie uzytkownika
    }

    public void gradeManagementClick() {
    }

    public void gradeOverviewClick() {
    }

    public void statisticsClick() {
    }

    public void studentProfilesClick() {
    }

    public void classManagementClick() {
    }

    public void subjectManagementClick() {
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
    }

}
