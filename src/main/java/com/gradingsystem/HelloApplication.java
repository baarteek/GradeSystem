package com.gradingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/login-view.fxml"));
            Scene loginScene = new Scene(root, 1600, 800);
            loginScene.getStylesheets().add(getClass().getResource("css/login-style.css").toExternalForm());

            stage.setTitle("Electronic Grading System");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/icon.png")));
            stage.setScene(loginScene);
            //stage.setResizable(false);
            stage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }
}