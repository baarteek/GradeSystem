package com.gradingsystem.controllers;

import com.gradingsystem.server.Database;
import com.gradingsystem.utils.ServerConnection;
import com.gradingsystem.utils.Validator;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.layout.HBox;


public class LoginController {
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private Label titleLabel;
    @FXML
    private Separator mainSeparator;
    @FXML
    private FlowPane loginFlowPane;
    @FXML
    private FlowPane titleFlowPane;
    @FXML
    private Label loginTitleLabel;
    @FXML
    private Separator loginSeparator;
    @FXML
    private HBox loginAsHBox;
    @FXML
    private Separator registerSeparator;
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


    public void initialize() {
        mainFlowPane.setAlignment(Pos.TOP_CENTER);
        loginFlowPane.setPrefWrapLength(200);
        mainFlowPane.setMargin(titleLabel, new Insets(20, 0, 20, 0));
        mainFlowPane.setMargin(loginFlowPane, new Insets(0, 0, 20, 0));
        mainFlowPane.setMargin(loginButton, new Insets(40, 0, 10, 0));
        mainFlowPane.setMargin(registerButton, new Insets(40, 0, 10, 0));
        mainFlowPane.setMargin(errorLabel, new Insets(10, 0, 0, 0));
        titleLabel.setMaxWidth(Double.MAX_VALUE);
        titleFlowPane.setHgap(Double.MAX_VALUE);
    }


    public void login(ActionEvent event) throws IOException {
        //ViewSwitcher.switchScene(event, root, teacherStage, teacherScene, "teacher-view", "teacher-style", this);
        if(checkLoginValidity()) {
            String email = loginTextField.getText();
            String password = passwordField.getText();

            ServerConnection serverConnection= new ServerConnection("localhost", 1025);
            serverConnection.connect();
            String requset = "LOGIN|" + email + "|" + password;
            String response = serverConnection.sendRequest(requset);
            serverConnection.disconnect();

            String[] loginResult = response.split("\\|");

            if(loginResult[0].equals("LOGIN_SUCCESS")) {
                if(loginResult[1].equals("TEACHER")) {
                    if(radioButtonTeacher.isSelected()) {
                        ViewSwitcher.switchScene(event, root, teacherStage, teacherScene, "teacher-view", "teacher-style", this);
                    } else {
                        errorLabel.setText("You can not log in as a student");
                    }
                } else if (loginResult[1].equals("STUDENT")) {
                    if(radioButtonStudent.isSelected()) {
                        System.out.println("zalogowano ucznia");
                    } else {
                        errorLabel.setText("You can not log in as a teacher");
                    }
                }
            } else if(loginResult[0].equals("LOGIN_FAILURE")) {
                errorLabel.setText("Incorrect login or password!");
            }
        }
    }

    private boolean checkLoginValidity() {
        if(loginTextField.getText().isEmpty() || passwordField.getText().isEmpty()) {
            errorLabel.setText("Fill in all fields");
            return false;
        }
        if(!radioButtonStudent.isSelected() && !radioButtonTeacher.isSelected()) {
            errorLabel.setText("Choose: Teacher or Student login");
            return false;
        }
        if(!Validator.validateEmail(loginTextField.getText())) {
            errorLabel.setText("Incorrect login or password");
            return false;
        }
        return true;
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, teacherStage, teacherScene, "register-view", "login-style", this);
    }
}