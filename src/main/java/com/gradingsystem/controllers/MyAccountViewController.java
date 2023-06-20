package com.gradingsystem.controllers;

import com.gradingsystem.userinfo.User;
import com.gradingsystem.utils.UserDataProvider;
import com.gradingsystem.utils.Validator;
import com.gradingsystem.utils.ViewSwitcher;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
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
    @FXML
    private Label nameMyAccount;
    @FXML
    private Label surnameMyAccount;
    @FXML
    private Label peselMyAccount;
    @FXML
    private Label emailMyAccount;
    @FXML
    private Label phoneNumberMyAccount;
    @FXML
    private HBox classesMyAccountHBox;
    @FXML
    private HBox subjectsMyAccountHBox;
    @FXML
    private Separator accountDetailsSeparator;
    @FXML
    private Separator menuSeparator2;
    @FXML
    private Separator menuSeparator4;
    @FXML
    private Separator menuSeparator5;
    @FXML
    private Separator menuSeparator6;
    @FXML
    private Separator menuSeparator7;
    @FXML
    private Separator menuSeparator8;
    @FXML
    private Separator accountOtherFieldsSeperator;
    @FXML
    private VBox accountMenuVBox;
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
    private Label accountOtherFieldsLabel;
    @FXML
    private ImageView logoutImage;
    private Parent root;
    private Stage stage;
    private Scene scene;
    private String name;
    private String surname;
    private String pesel;
    private String phoneNumber;
    private String email;
    private String password;

    public void initialize() {
        ViewSwitcher.switchMenuIcons(this, gradeManagementImage, gradeOverviewImage, statisticsImage, studentProfilesImage, classManagementImage, subjectManagementImage, notificationsImage, messagesImage, settingsImage, logoutImage, emailImageView,  profileImageView, settingsImageView);
        String[] userData;
        String cssType;
        if (User.getType() == "teacher") {
            userData = UserDataProvider.getUserData("nauczyciel", LoginController.userID);

            hideElementsForTeacher();
        } else if (User.getType() == "admin") {
            userData = UserDataProvider.getUserData("admin", LoginController.userID);
        }
        else {
            userData = UserDataProvider.getUserData("uczen", LoginController.userID);

            hideElementsForStudent();
        }

        if(!userData[0].equals("GET_USER_DATA_FAILURE")) {
            if (User.getType() == "teacher" || User.getType() == "admin") {
                name = userData[2];
                surname = userData[3];
                pesel = userData[4];
                email = userData[5];
                phoneNumber = userData[6];
                password = userData[7];
            }
            else {
                name = userData[3];
                surname = userData[4];
                pesel = userData[5];
                email = userData[6];
                phoneNumber = userData[7];
                password = userData[8];
            }
            updateUserFields();
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("USER DATA");
            alert.setContentText("Failed to fetch user data");
            alert.showAndWait();
        }

    }

    private void hideElementsForTeacher() {
        accountMenuVBox.getChildren().remove(classManagementHBox);
        accountMenuVBox.getChildren().remove(studentProfilesHBox);
        accountMenuVBox.getChildren().remove(subjectManagementHBox);
        accountMenuVBox.getChildren().remove(messagesHBox);
        accountMenuVBox.getChildren().remove(menuSeparator5);
        accountMenuVBox.getChildren().remove(menuSeparator6);
        accountMenuVBox.getChildren().remove(menuSeparator4);

    }

    private void hideElementsForStudent() {
        accountMenuVBox.getChildren().remove(classesMyAccountHBox);
        accountMenuVBox.getChildren().remove(subjectsMyAccountHBox);
        accountMenuVBox.getChildren().remove(gradeManagementHBox);
        accountMenuVBox.getChildren().remove(menuSeparator2);
        accountMenuVBox.getChildren().remove(studentProfilesHBox);
        accountMenuVBox.getChildren().remove(menuSeparator4);
        accountMenuVBox.getChildren().remove(classManagementHBox);
        accountMenuVBox.getChildren().remove(subjectManagementHBox);
        accountMenuVBox.getChildren().remove(menuSeparator5);
        accountMenuVBox.getChildren().remove(menuSeparator6);
        accountMenuVBox.getChildren().remove(menuSeparator7);
        accountMenuVBox.getChildren().remove(menuSeparator8);
        accountMenuVBox.getChildren().remove(notificationsHBox);
        accountMenuVBox.getChildren().remove(messagesHBox);

        accountOtherFieldsSeperator.setVisible(false);
        accountOtherFieldsLabel.setVisible(false);
        nameMyAccountRadioButton.setVisible(false);
        surnameMyAccountRadioButton.setVisible(false);
        peselMyAccountRadioButton.setVisible(false);
        emailMyAccountRadioButton.setVisible(false);
        phoneNumberMyAccountRadioButton.setVisible(false);
        otherFieldsRadioButton.setVisible(false);
        newValueMyAccountLabel.setVisible(false);
        valueMyAccountTestField.setVisible(false);
        editMyAccountButton.setVisible(false);

        accountMenuVBox.requestLayout();
    }

    private void updateUserFields() {
        userNameLabel.setText(name + " " + surname);
        emailLabel.setText(email);
        emailMyAccount.setText(email);
        nameMyAccount.setText(name);
        surnameMyAccount.setText(surname);
        peselMyAccount.setText(pesel);
        phoneNumberMyAccount.setText(phoneNumber);
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
        ViewSwitcher.switchScene(event, root, stage, scene, "login-view", "login-style", this);
    }

    public void changePassword() {
        System.out.println("password");
        if(checkPassword()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("PASSWORD");

            String newPassword = newPasswordField.getText();

            String changeDataResult = "";
            if(User.getType().equals("student")) {
                changeDataResult = UserDataProvider.changeUserData("uczen", LoginController.userID, "haslo", newPassword);
            } else if (User.getType().equals("teacher")) {
                changeDataResult = UserDataProvider.changeUserData("nauczyciel", LoginController.userID, "haslo", newPassword);
            } else if(User.getType().equals("admin")) {
                changeDataResult = UserDataProvider.changeUserData("admin", LoginController.userID, "haslo", newPassword);
            }

            if(changeDataResult.equals("CHANGE_USER_DATA_SUCCESS")) {
                alert.setContentText("Password has been changed");
                alert.showAndWait();
                password = newPassword;
                passwordRadioButton.setSelected(false);
                passwordRadioButtonClick();
                newPasswordField.setText("");
                repeatNewPasswordField.setText("");
                currentPasswordField.setText("");
            } else if(changeDataResult.equals("CHANGE_USER_DATA_FAILURE")) {
                alert.setContentText("Password could not be changed");
                alert.showAndWait();
            }
        }
    }

    public boolean processUserDataChange(String column, int userID, String value) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("USER DATA");

        String tableName= "";
        if(User.getType().equals("student")) {
            tableName = "uczen";
        } else if (User.getType().equals("teacher")) {
            tableName = "nauczyciel";
        } else if(User.getType().equals("admin")) {
            tableName = "admin";
        }

        String changeDataResult = UserDataProvider.changeUserData(tableName, userID, column, value);

        if(changeDataResult.equals("CHANGE_USER_DATA_SUCCESS")) {
            alert.setContentText("Field has been changed");
            alert.showAndWait();
            return true;
        } else if(changeDataResult.equals("CHANGE_USER_DATA_FAILURE")) {
            alert.setContentText("Field could not be changed");
            alert.showAndWait();
        }
        return false;
    }

    public void changeUserData() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("USER DATA");

        String value = valueMyAccountTestField.getText();

        String tableName= "";
        if(User.getType().equals("student")) {
            tableName = "uczen";
        } else if (User.getType().equals("teacher")) {
            tableName = "nauczyciel";
        } else if(User.getType().equals("admin")) {
            tableName = "admin";
        }

        if(nameMyAccountRadioButton.isSelected()) {
            if(processUserDataChange("imie", LoginController.userID, value)) {
                name = value;
                userNameLabel.setText(name + " " + surname);
                nameMyAccount.setText(name + " " + surname);
            }
        }else if(surnameMyAccountRadioButton.isSelected()) {
            if(processUserDataChange("nazwisko", LoginController.userID, value)) {
                surname = value;
                userNameLabel.setText(name + " " + surname);
                nameMyAccount.setText(name + " " + surname);
            }
        }else if(peselMyAccountRadioButton.isSelected()) {
            if(Validator.validatePESEL(value)) {
                if(UserDataProvider.isDataInDatabase(tableName, "pesel", value)) {
                    alert.setContentText("There is already a user with this pesel");
                    alert.showAndWait();
                } else {
                    if(processUserDataChange("pesel", LoginController.userID, value)) {
                        pesel = value;
                        peselMyAccount.setText(pesel);
                    }
                }
            } else {
                alert.setContentText("Incorrect PESEL");
                alert.showAndWait();
            }
        }else if(emailMyAccountRadioButton.isSelected()) {
            if(Validator.validateEmail(value)) {
                if(UserDataProvider.isDataInDatabase(tableName, "email", value)) {
                    alert.setContentText("There is already a user with this E-mail");
                    alert.showAndWait();
                } else {
                    if(processUserDataChange("email", LoginController.userID, value)) {
                        email = value;
                        emailMyAccount.setText(email);
                    }
                }
            } else {
                alert.setContentText("Incorrect E-mail");
                alert.showAndWait();
            }
        } else if(phoneNumberMyAccountRadioButton.isSelected()) {
            if(Validator.validatePhoneNumber(value)) {
                if(UserDataProvider.isDataInDatabase(tableName, "telefon", value)) {
                    alert.setContentText("There is already a user with this phone number");
                    alert.showAndWait();
                } else {
                    if(processUserDataChange("telefon", LoginController.userID, value)) {
                        phoneNumber = value;
                        phoneNumberMyAccount.setText(phoneNumber);
                    }
                }
            } else {
                alert.setContentText("Incorrect phone number");
                alert.showAndWait();
            }
        }
        valueMyAccountTestField.setText("");
        otherFieldsRadioButton.setSelected(false);
        otherFieldsRadioButtonClick();
    }

    public boolean checkPassword() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("PASSWORD");

        String newPassword = newPasswordField.getText();
        String repeatNewPassword = repeatNewPasswordField.getText();
        String currentPassword = currentPasswordField.getText();

        if(Validator.validatePassword(newPassword)) {
            if(newPassword.equals(repeatNewPassword)) {
                if(password.equals(currentPassword)) {
                    return true;
                } else {
                    alert.setContentText("You entered an incorrect current password");
                    alert.showAndWait();
                }
            } else {
                alert.setContentText("New passwords entered are not identical");
                alert.showAndWait();
            }
        } else {
            alert.setContentText("Password must be between 9 and 20 characters long, contain at least one number, one capital letter and one special character");
            alert.showAndWait();
        }
        return false;
    }

    public void gradeManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "grade-management-view", User.getCssFileName(), this);
    }

    public void gradeOverviewClick(MouseEvent event) throws IOException  {
        if (User.getType() == "student") {
            ViewSwitcher.switchScene(event, root, stage, scene, "student-grades-view", User.getCssFileName(), this);
        }
    }

    public void statisticsClick(MouseEvent event) throws IOException  {
        if (User.getType() == "student") {
            ViewSwitcher.switchScene(event, root, stage, scene, "student-statistics-view", User.getCssFileName(), this);
        } else{
                ViewSwitcher.switchScene(event, root, stage, scene, "statistics-view", User.getCssFileName(), this);
        }
    }

    public void studentProfilesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "student-profiles-view", User.getCssFileName(), this);
    }

    public void classManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "class-management-view", User.getCssFileName(), this);
    }

    public void subjectManagementClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "subject-management-view", User.getCssFileName(), this);
    }

    public void notificationsClick() {
    }

    public void messagesClick(MouseEvent event) throws IOException {
        ViewSwitcher.switchScene(event, root, stage, scene, "register-view", User.getCssFileName(), this);
    }

}
