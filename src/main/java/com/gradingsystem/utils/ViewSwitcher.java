package com.gradingsystem.utils;

import com.gradingsystem.userinfo.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewSwitcher {
    public static void switchScene(MouseEvent event, Parent root, Stage stage, Scene scene, String viewName, String cssName, Object thisClass) throws IOException {
        try {
            String viewPath = "/com/gradingsystem/views/" + viewName +".fxml";
            String cssPath = "/com/gradingsystem/css/"+ cssName + ".css";
            root = FXMLLoader.load(thisClass.getClass().getResource(viewPath));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1600, 800);
            scene.getStylesheets().add(thisClass.getClass().getResource(cssPath).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void switchScene(ActionEvent event, Parent root, Stage stage, Scene scene, String viewName, String cssName, Object thisClass) throws IOException {
        try {
            String viewPath = "/com/gradingsystem/views/" + viewName +".fxml";
            String cssPath = "/com/gradingsystem/css/"+ cssName + ".css";
            root = FXMLLoader.load(thisClass.getClass().getResource(viewPath));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root, 1600, 800);
            scene.getStylesheets().add(thisClass.getClass().getResource(cssPath).toExternalForm());
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public static void setImage(Object thisclass, ImageView imageView, String imagePath) {
        URL url = thisclass.getClass().getResource(imagePath);
        Image image = new Image(url.toExternalForm());
        imageView.setImage(image);
    }

    public static void switchMenuIcons(Object thisclass, ImageView gradeManagementImage, ImageView gradeOverviewImage, ImageView statisticsImage, ImageView studentProfilesImage, ImageView classManagementImage, ImageView subjectManagementImage, ImageView notificationsImage, ImageView messagesImage, ImageView settingsImage, ImageView logoutImage, ImageView emailImageView, ImageView profileImageView, ImageView settingsImageView) {
        String path = "";
        if(User.getType().equals("admin")) {
            path = "/com/gradingsystem/icons/adminIcons";
            setImage(thisclass, profileImageView, path + "/user.png");
            setImage(thisclass, gradeManagementImage, path + "/plus.png");
            setImage(thisclass, gradeOverviewImage, path + "/eye.png");
            setImage(thisclass, statisticsImage, path + "/statistics.png");
            setImage(thisclass, studentProfilesImage, path + "/user.png");
            setImage(thisclass, classManagementImage, path + "/class.png");
            setImage(thisclass, subjectManagementImage, path + "/books.png");
            setImage(thisclass, notificationsImage, path + "/notifications.png");
            setImage(thisclass, messagesImage, path + "/chat.png");
            setImage(thisclass, settingsImage, path + "/settings.png");
            setImage(thisclass, logoutImage, path + "/logout.png");
            setImage(thisclass, emailImageView, path + "/mail.png");
            setImage(thisclass, settingsImageView, path + "/settings.png");
        } else if (User.getType().equals("student")) {
            path = "/com/gradingsystem/icons/studentIcons";
            setImage(thisclass, profileImageView, path + "/user.png");
            setImage(thisclass, gradeManagementImage, path + "/plus.png");
            setImage(thisclass, gradeOverviewImage, path + "/eye.png");
            setImage(thisclass, statisticsImage, path + "/statistics.png");
            setImage(thisclass, studentProfilesImage, path + "/user.png");
            setImage(thisclass, classManagementImage, path + "/class.png");
            setImage(thisclass, subjectManagementImage, path + "/books.png");
            setImage(thisclass, notificationsImage, path + "/notifications.png");
            setImage(thisclass, messagesImage, path + "/chat.png");
            setImage(thisclass, settingsImage, path + "/settings.png");
            setImage(thisclass, logoutImage, path + "/logout.png");
            setImage(thisclass, emailImageView, path + "/mail.png");
            setImage(thisclass, settingsImageView, path + "/settings.png");
        } else if (User.getType().equals("teacher")) {
            path = "/com/gradingsystem/icons/teacherIcons";
            setImage(thisclass, profileImageView, path + "/user.png");
            setImage(thisclass, gradeManagementImage, path + "/plus.png");
            setImage(thisclass, gradeOverviewImage, path + "/eye.png");
            setImage(thisclass, statisticsImage, path + "/statistics.png");
            setImage(thisclass, notificationsImage, path + "/notifications.png");
            setImage(thisclass, settingsImage, path + "/settings.png");
            setImage(thisclass, logoutImage, path + "/logout.png");
            setImage(thisclass, emailImageView, path + "/mail.png");
            setImage(thisclass, settingsImageView, path + "/settings.png");
        }
    }


}
