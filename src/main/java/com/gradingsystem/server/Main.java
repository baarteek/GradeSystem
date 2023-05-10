package com.gradingsystem.server;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Database.connect();
        Database.create_tables();

        try {
            int port = 1000;
            ServerSocket serverSocket = new ServerSocket(port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                Thread thread = new Thread(() -> {
                    try {
                        InputStream inputStream = clientSocket.getInputStream();
                        byte[] buffer = new byte[1024];
                        int bytesRead = inputStream.read(buffer);
                        String message = new String(buffer, 0, bytesRead);
                        System.out.println("Received message: " + message);

                        OutputStream outputStream = clientSocket.getOutputStream();
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);

                        Map<String, Object> klasaFields = Database.getAllKlasaFields();
                        System.out.println(klasaFields);

                        objectOutputStream.writeObject(klasaFields);

                        clientSocket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });

                thread.start();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            Database.disconnect();
        }
    }
}


