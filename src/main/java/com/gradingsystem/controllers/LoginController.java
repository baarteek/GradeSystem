package com.gradingsystem.controllers;

import com.gradingsystem.utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;


public class LoginController {
    @FXML
    private TextField loginTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Button registerButton;
    @FXML
    private Label errorLabel;
    @FXML
    private RadioButton radioButtonStudent;
    @FXML
    private RadioButton radioButtonTeacher;
    private Stage stage;
    private Stage teacherStage;
    private Scene registerScene;
    private Scene teacherScene;
    private Parent root;

    public void login(ActionEvent event) throws IOException {
        errorLabel.setText("Incorrect login or password");
        switchToTeacherrScene(event);

        //dodac obsluge logowania
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/gradingsystem/views/register-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            registerScene = new Scene(root, 1600, 800);
            registerScene.getStylesheets().add(getClass().getResource("/com/gradingsystem/css/login-style.css").toExternalForm());
            stage.setScene(registerScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void switchToTeacherrScene(ActionEvent event) throws IOException {
        try {
            root = FXMLLoader.load(getClass().getResource("/com/gradingsystem/views/teacher-view.fxml"));
            teacherStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            teacherScene = new Scene(root, 1600, 800);
            teacherScene.getStylesheets().add(getClass().getResource("/com/gradingsystem/css/teacher-style.css").toExternalForm());
            teacherStage.setScene(teacherScene);
            teacherStage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
