package com.gradingsystem.server;

import com.gradingsystem.utils.DataPresenter;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.gradingsystem.server.Database.add_test_data;
import static com.gradingsystem.server.Database.getStudentsWithoutClass;

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

                        String response = "";
                        switch (operationCode) {
                            case "LOGIN":
                                response = handleLogin(parts);
                                break;
                            case "REGISTER_TEACHER":
                            case "REGISTER_STUDENT":
                                response = handleRegister(parts, operationCode);
                                break;
                            case "GET_USER_DATA":
                                response = handleGetUserData(parts);
                                break;
                            case "CHANGE_USER_DATA":
                                response = handleChangeUserData(parts);
                                break;
                            case "CHECK_DATA_EXISTT":
                                response = handleCheckDataExist(parts);
                                break;
                            case "GET_STUDENTS_WITHOUT_CLASS":
                                response = handleGetStudentsWithoutClass();
                                break;
                            case "ADD_CLASS":
                                response = handleAddClass(parts[1]);
                                break;
                            case "ADD_SUBJECT":
                                response = handleAddSubjcect(parts[1]);
                                break;
                            case "ADD_STUIDENT_TO_CLASS":
                                response = handlAddStudentToClass(parts[1].toUpperCase(), parts[2]);
                                break;
                            case "GET_ALL_DATA_FROM_TABLE":
                                response = handleGetAllDataFromTable(parts[1]);
                                break;
                            case "UPDATE_FIELDS":
                                response = handleUdateFields(parts[1], parts[2], parts[3], parts[4]);
                                break;
                            case "DELETE_RECORD":
                                response = handleDeleteRecord(parts[1], parts[2], parts[3]);
                                break;
                            case "GET_TABLE_DATA":
                                response = handleGetTableData(parts[1], parts[2], parts[3]);
                                break;
                            case "ADD_LINK":
                                response = handleAddLink(parts[1], parts[2]);
                                break;
                            case "GET_STUDENT_SUBJECTS_AND_GRADES":
                                response = handleGetStudentSubjectsAndGrades(parts[1]);
                                break;
                        }

                        bufferedWriter.write(response);
                        bufferedWriter.flush();
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

    private static String handleAddLink(String classID, String subjectID) {
        String condition = "klasa_id='" + classID + "' AND przedmiot_id='" + subjectID + "'";
        String result = Database.getTableData("klasa_przedmiot", "id", condition);
        if(result.isEmpty()) {
            int idClass = Integer.parseInt(classID);
            int idSubject = Integer.parseInt(subjectID);
            Database.add_klasa_przedmiot(idClass, idSubject);
            return "ADD_LINK_SUCCESS";
        } else {
            return "ADD_LINK_FAILURE";
        }
    }

    private static String handleAddSubjcect(String subjectName) {
        String result = Database.checkIfDataExists("przedmiot", new String[]{"nazwa"}, new String[]{subjectName});
        if(result.equals("SUCCESS")) {
            Database.add_przedmiot(subjectName);
            return "ADD_SUBJECT_SUCCESS";
        } else {
            return "ADD_SUBJECT_FAILURE";
        }
    }

    private static String handleGetTableData(String tableName, String column, String condition) {
        String result = Database.getTableData(tableName, column, condition);
        if(result.isEmpty()) {
            return "GET_TABLE_DATA_FAILURE";
        } else {
            return result;
        }
    }

    private static String handleDeleteRecord(String tableName, String id, String fieldName) {
        if(Database.deleteRecordById(tableName, Integer.parseInt(id), fieldName)) {
            return "DELETE_RECORD_SUCCESS";
        } else {
            return "DELETE_RECORD_FAILURE";
        }
    }

    private static String handleUdateFields(String tableName, String fieldName, String newValue, String conditionField) {
        if(Database.updateField(tableName, fieldName, newValue, conditionField)) {
            return "UPDATE_FIELDS_SUCCESS";
        } else {
            return "UPDATE_FIELDS_FAILURE";
        }
    }

    private static String handleGetAllDataFromTable(String tableName) {
        tableName.toLowerCase();
        List<String> data = Database.getAllFieldsFromTable(tableName);
        String result = data.toString();
        result =  result.substring(1, result.length() - 1);
        return "GET_ALL_DATA_FROM_TABLE|SUCCESS|" + result;
    }

    private static String handlAddStudentToClass(String className, String idStudent) {
        int studentID = Integer.parseInt(idStudent);
        String classData = Database.getUserDataByName("klasa", "nazwa", className);
        String[] parts = classData.split("\\|");
        String classID = parts[0];
        return Database.changeUserData("uczen", studentID, "klasa_id", classID);
    }

    private static String handleGetStudentsWithoutClass() {
        String studentsData = getStudentsWithoutClass().toString();
        if (studentsData == null) {
            return "GET_STUDENTS_WITHOUT_CLASS_FAILURE";
        } else {
            return "GET_STUDENTS_WITHOUT_CLASS_SUCCESS|" + studentsData;
        }
    }

    private static String handleLogin(String[] parts) {
        String email = parts[1];
        String password = parts[2];
        return Database.checkCredentials(email, password);
    }

    private static String handleAddClass(String nameClass) {
        String result = Database.checkIfDataExists("klasa", new String[]{"nazwa"}, new String[]{nameClass.toUpperCase()});
        if(result.equals("SUCCESS")) {
            Database.add_klasa(nameClass);
            return "ADD_CLASS_SUCCESS";
        } else {
            return "ADD_CLASS_FAILURE";
        }
    }

    private static String handleRegister(String[] parts, String operationCode) {
        String name = parts[1];
        String surname = parts[2];
        String email = parts[3];
        String password = parts[4];
        String pesel = parts[5];
        String phoneNumber = parts[6];
        String[] columnNames = {"pesel", "email", "telefon"};
        String[] values = {pesel, email, phoneNumber};
        String registerResult = "";
        if (operationCode.equals("REGISTER_TEACHER")) {
            String tableName = "nauczyciel";
            registerResult = Database.checkIfDataExists(tableName, columnNames, values);
            if (registerResult.equals("SUCCESS")) {
                Database.add_nauczyciel(name, surname, pesel, email, phoneNumber, password);
            }
        }
        if (operationCode.equals("REGISTER_STUDENT")) {
            String tableName = "uczen";
            registerResult = Database.checkIfDataExists(tableName, columnNames, values);
            if (registerResult.equals("SUCCESS")) {
                Database.add_uczen(name, surname, pesel, email, phoneNumber, password);
            }
        }
        return "REGISTER_" + registerResult;
    }

    private static String handleGetUserData(String[] parts) {
        int userID = Integer.parseInt(parts[2]);
        String findUserResult = "";
        if (parts[1].equals("TEACHER")) {
            findUserResult = Database.getUserDataById("nauczyciel", userID);
        } else if (parts[1].equals("STUDENT")) {
            findUserResult = Database.getUserDataById("uczen", userID);
        }
        if (findUserResult == null) {
            return "GET_USER_DATA_FAILURE";
        } else {
            return "GET_USER_DATA_SUCCESS|" + findUserResult;
        }
    }

    private static String handleChangeUserData(String[] parts) {
        int userID = Integer.parseInt(parts[3]);
        String column = parts[2];
        String value = parts[4];
        String changeUserDataResult = "";
        if (parts[1].equals("TEACHER")) {
            changeUserDataResult = Database.changeUserData("nauczyciel", userID, column, value);
        } else if (parts[1].equals("STUDENT")) {
            changeUserDataResult = Database.changeUserData("uczen", userID, column, value);
        }
        return changeUserDataResult;
    }

    private static String handleCheckDataExist(String[] parts) {
        String tableName = parts[1].toUpperCase();
        String columnNames = parts[2];
        String values = parts[3];
        String userDataResult = "";
        userDataResult = Database.checkIfDataExists(tableName, new String[]{columnNames}, new String[]{values});
        return userDataResult;
    }

    private static String handleGetStudentSubjectsAndGrades(String userId) {
        String subjects = Database.getStudentSubjectsAndGrades(userId);

        if (subjects == null) {
            return "GET_STUDENT_SUBJECTS_DATA_FAILURE";
        }
        else {
            return "GET_STUDENT_SUBJECTS_DATA_SUCCESS|" + subjects;
        }
    }
}
