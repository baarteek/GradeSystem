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
                        } else if (operationCode.equals("REGISTER_TEACHER") || operationCode.equals("REGISTER_STUDENT")) {
                            String name = parts[1];
                            String surname = parts[2];
                            String email = parts[3];
                            String password = parts[4];
                            String pesel = parts[5];
                            String phoneNumber = parts[6];
                            String[] columnNames = {"pesel", "email", "telefon"};
                            String[] values = {pesel, email, phoneNumber};
                            String registerResult = new String();
                            if(operationCode.equals("REGISTER_TEACHER")) {
                                String tableName = "nauczyciel";
                                registerResult = Database.checkIfDataExists(tableName, columnNames, values);
                                if(registerResult.equals("SUCCESS")) {
                                    Database.add_nauczyciel(name, surname, pesel, email, phoneNumber, password);
                                }
                            }
                            if(operationCode.equals("REGISTER_STUDENT")) {
                                String tableName = "uczen";
                                registerResult = Database.checkIfDataExists(tableName, columnNames, values);
                                if(registerResult.equals("SUCCESS")) {
                                    Database.add_uczen(1, name, surname, pesel, email, phoneNumber, password);
                                }
                            }
                            String mess = "REGISTER_" + registerResult;
                            bufferedWriter.write(mess);
                            bufferedWriter.flush();
                        } else if(operationCode.equals("GET_USER_DATA")) {
                            int userID = Integer.parseInt(parts[2]);
                            String findUserResult = new String();
                            if(parts[1].equals("TEACHER")) {
                                findUserResult = Database.getUserDataById("nauczyciel", userID);
                            } else if (parts[1].equals("STUDENT")) {
                                findUserResult = Database.getUserDataById("uczen", userID);
                            }
                            String mess = new String();
                            if(findUserResult == null) {
                                mess = "GET_USER_DATA_FAILURE";
                            } else {
                                mess = "GET_USER_DATA_SUCCESS|" + findUserResult;
                            }
                            System.out.println(mess);
                            bufferedWriter.write(mess);
                            bufferedWriter.flush();
                        } else if (operationCode.equals("CHANGE_USER_DATA")) {
                            int userID = Integer.parseInt(parts[3]);
                            String column = parts[2];
                            String value = parts[4];
                            String changeUserDataResult = new String();
                            if(parts[1].equals("TEACHER")) {
                                changeUserDataResult = Database.changeUserData("nauczyciel", userID, column, value);
                            } else if (parts[1].equals("STUDENT")) {
                                changeUserDataResult = Database.changeUserData("uczen", userID, column, value);
                            }
                            bufferedWriter.write(changeUserDataResult);
                            bufferedWriter.flush();
                        } else if(operationCode.equals("CHECK_DATA_EXISTT")){
                            String userDataResult = new String();
                            String columnNames = parts[2];
                            String values = parts[3];
                            if(parts[1].equals("TEACHER")) {
                                userDataResult = Database.checkIfDataExists("nauczyciel", new String[]{columnNames}, new String[]{values});
                            } else if (parts[1].equals("STUDENT")) {
                                userDataResult = Database.checkIfDataExists("uczen", new String[]{columnNames}, new String[]{values});
                            }
                            bufferedWriter.write(userDataResult);
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


