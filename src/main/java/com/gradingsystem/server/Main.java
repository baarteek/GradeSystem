package com.gradingsystem.server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        Database.connect();
        Database.create_tables();

        try {
            int port = 1025;
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
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
                        BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                        String[] parts = message.split("\\|");
                        String operationCode = parts[0];

                        if(operationCode.equals("LOGIN")) {
                            String email = parts[1];
                            String password = parts[2];
                            String loginResult = Database.checkCredentials(email, password);
                            System.out.println(loginResult);
                            bufferedWriter.write(loginResult);
                            bufferedWriter.flush();
                        }

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


