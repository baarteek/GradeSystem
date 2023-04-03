package com.gradingsystem.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

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
}
