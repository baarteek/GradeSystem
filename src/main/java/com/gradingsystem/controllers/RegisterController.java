package com.gradingsystem.controllers;

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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
            errorLabel.setText("Password must be between 9 and 20 characters long, contain at least\none number, one capital letter and one special characterl");
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
        return true;
    }

    public void addNewUser(ActionEvent event) {
        if(checkDataValidity()) {

        }
    }

    public void switchToLoginScene(ActionEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, loginScene, "login-view", "login-style", this);
    }
}
