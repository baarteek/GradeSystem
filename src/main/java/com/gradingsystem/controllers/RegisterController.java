package com.gradingsystem.controllers;

import com.gradingsystem.utils.Validator;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.Buffer;

public class RegisterController {
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
    private Button registerButton;
    @FXML
    private Button backButton;
    @FXML
    private Label errorLabel;
    private Stage stage;
    private Scene loginScene;
    private Parent root;

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
        try {
            root = FXMLLoader.load(getClass().getResource("/com/gradingsystem/views/login-view.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            loginScene = new Scene(root, 1600, 800);
            stage.setScene(loginScene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
