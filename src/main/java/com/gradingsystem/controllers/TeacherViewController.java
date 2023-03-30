package com.gradingsystem.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

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
