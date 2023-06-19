package com.gradingsystem.controllers;

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
import javafx.stage.Stage;

import javax.security.auth.PrivateCredentialPermission;
import java.io.IOException;
import java.nio.Buffer;
import java.util.PrimitiveIterator;
import java.util.concurrent.Flow;

public class RegisterController {
    @FXML
    private FlowPane mainFlowPane;
    @FXML
    private FlowPane titleFlowPane;
    @FXML
    private FlowPane registerFlowPane;
    @FXML
    private HBox registerHBox;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField surnameTextField;
    @FXML
    private TextField emailTextField;
    @FXML
    private TextField peselTextField;
    @FXML
    private TextField phoneNumberTextField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private RadioButton radioButtonTeacher;
    @FXML
    private RadioButton radioButtonStudent;
    @FXML
    private Button register2Button;
    @FXML
    private Button backButton;
    @FXML
    private Label titleLabel;
    @FXML
    private Label errorLabel;
    private Stage stage;
    private Scene loginScene;
    private Parent root;

    @FXML
    private void initialize() {
        mainFlowPane.setPrefWidth(Double.MAX_VALUE);
        mainFlowPane.setPrefHeight(Double.MAX_VALUE);
        mainFlowPane.setAlignment(Pos.CENTER);
        mainFlowPane.setPadding(new Insets(50, 100, 50, 100));
    }

    public boolean checkDataValidity() {
        if(nameTextField.getText().isEmpty() || surnameTextField.getText().isEmpty()) {
            errorLabel.setText("Fill in all fields");
            return false;
        }
        if(!Validator.validateEmail(emailTextField.getText())) {
            errorLabel.setText("Invalid E-mail");
            return false;
        }
        if(!Validator.validatePassword(passwordField.getText())) {
            errorLabel.setText("Password must be between 9 and 20 characters long,\ncontain at least one number, one capital letter and one\nspecial character");
            return false;
        }
        if(!Validator.validatePESEL(peselTextField.getText())) {
            errorLabel.setText("Incorrect PESEL");
            return false;
        }
        if(!Validator.validatePhoneNumber(phoneNumberTextField.getText())) {
            errorLabel.setText("Incorrect phone number");
            return false;
        }
        if(!radioButtonStudent.isSelected() && !radioButtonTeacher.isSelected()) {
            errorLabel.setText("Choose account type");
        }
        return true;
    }

    public void addNewUser(ActionEvent event) throws IOException {
        if(checkDataValidity()) {
            String name = nameTextField.getText();
            String surname = surnameTextField.getText();
            String email = emailTextField.getText();
            String password = passwordField.getText();
            String pesel = peselTextField.getText();
            String phoneNumber = phoneNumberTextField.getText();

            ServerConnection serverConnection = new ServerConnection("localhost", 1025);
            serverConnection.connect();
            String requset = new String();

            if(radioButtonTeacher.isSelected()) {
                requset = "REGISTER_TEACHER|" + name + "|" + surname + "|" + email + "|" + password + "|" + pesel + "|" + phoneNumber;
            } else if(radioButtonStudent.isSelected()) {
                requset = "REGISTER_STUDENT|" + name + "|" + surname + "|" + email + "|" + password + "|" + pesel + "|" + phoneNumber;
            }

            String response = serverConnection.sendRequest(requset);
            serverConnection.disconnect();

            String[] registerResult = response.split("\\|");

            if(registerResult[0].equals("REGISTER_SUCCESS")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText("REGISTER");
                alert.setContentText("New user added");
                alert.showAndWait();
                //switchToLoginScene(event);
                errorLabel.setText("");
            } else if(registerResult[0].equals("REGISTER_FAILURE")) {
                registerErrorMessage(registerResult[1]);
            }
        }
    }

    public void registerErrorMessage(String error) {
        if(error.equals("PESEL")) {
            errorLabel.setText("There is a user with this pesel");
        }
        if(error.equals("TELEFON")) {
            errorLabel.setText("There is a user with this phone number");
        }
        if(error.equals("EMAIL")) {
            errorLabel.setText("There is a user with this E-mail");
        }
    }

    public void switchToLoginScene(ActionEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, loginScene, "login-view", "login-style", this);
    }

    public void switchToAdminScene(ActionEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, loginScene, "admin-view", "login-style", this);
    }
}
