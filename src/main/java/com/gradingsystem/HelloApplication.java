package com.gradingsystem;

import com.gradingsystem.server.Database;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Map;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) {
        // test connection client-server -- start
        try {
            String host = "localhost";
            int port = 1025;
            Socket socket = new Socket(host, port);

            // sending msg
            OutputStream outputStream = socket.getOutputStream();
            String message = "test";
            outputStream.write(message.getBytes());

            // receiving msg Map<String, Object>
            InputStream inputStream = socket.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            Map<String, Object> receivedUserValues = (Map<String, Object>) objectInputStream.readObject();
            System.out.println(receivedUserValues);


            socket.close();
        } catch (IOException e) {
            System.out.println("IOException.");
        }
        catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException.");
        }
        // test connection client-server -- end

        try {
            Parent root = FXMLLoader.load(getClass().getResource("views/login-view.fxml"));
            Scene loginScene = new Scene(root, 1600, 800);
            loginScene.getStylesheets().add(getClass().getResource("css/login-style.css").toExternalForm());

            stage.setTitle("Electronic Grading System");
            stage.getIcons().add(new Image(getClass().getResourceAsStream("icons/icon.png")));
            stage.setScene(loginScene);
            stage.setResizable(false);
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