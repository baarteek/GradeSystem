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
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
import java.io.IOException;
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
        errorLabel.setText("Incorrect login or password");
        ViewSwitcher.switchScene(event, root, teacherStage, teacherScene, "teacher-view", "teacher-style", this);

        //dodac obsluge logowania
    }

    public void switchToRegisterScene(ActionEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, teacherStage, teacherScene, "register-view", "login-style", this);
    }


}
