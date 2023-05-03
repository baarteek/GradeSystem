package com.gradingsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // test connection client-server
        String host = "localhost";
        int port = 1000;
        Socket socket = new Socket(host, port);

        OutputStream outputStream = socket.getOutputStream();
        String message = "Hello, server!";
        outputStream.write(message.getBytes());

        InputStream inputStream = socket.getInputStream();
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);
        String response = new String(buffer, 0, bytesRead);
        System.out.println("Received response: " + response);

        socket.close();

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