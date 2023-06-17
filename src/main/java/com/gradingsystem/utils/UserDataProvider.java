package com.gradingsystem.utils;

import com.gradingsystem.controllers.LoginController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.util.*;

public class UserDataProvider {
    public static String[] getUserData(String tableName, int userID) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        if(tableName.equals("nauczyciel")) {
            requset = "GET_USER_DATA|TEACHER|" + LoginController.userID;
        } else if(tableName.equals("uczen")) {
            requset = "GET_USER_DATA|STUDENT|" + LoginController.userID;
        } else if(tableName.equals("admin")) {
            requset = "GET_USER_DATA|ADMIN|" + LoginController.userID;
        }
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();

        String[] userData = response.split("\\|");

        if(userData[0].equals("GET_USER_DATA_SUCCESS")) {
            return userData;
        }
        return new String[]{"GET_USER_DATA_FAILURE"};
    }

    public static String changeUserData(String tableName, int userID, String column, String value) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        if(tableName.equals("nauczyciel")) {
            requset = "CHANGE_USER_DATA|TEACHER|" + column + "|" + LoginController.userID + "|" + value;
        } else if(tableName.equals("uczen")) {
            requset = "CHANGE_USER_DATA|STUDENT|" + column + "|" + LoginController.userID + "|" + value;
        } else if (tableName.equals("admin")) {
            requset = "CHANGE_USER_DATA|ADMIN|" + column + "|" + LoginController.userID + "|" + value;
        }
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();

        return response;
    }

    public static boolean isDataInDatabase(String tableName, String column, String value) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        requset = "CHECK_DATA_EXISTT|" + tableName.toUpperCase() + "|" + column + "|"  + value;
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return !response.equals("SUCCESS");
    }

    public static String addNewClass(String calssName) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        requset = "ADD_CLASS|" + calssName;
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return response;
    }

    public static String addNewSubjcet(String subjectName) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        requset = "ADD_SUBJECT|" + subjectName;
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return response;
    }

    public static String addLinkBetweenClassAndSubject(String classID, String subjectID) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        requset = "ADD_LINK|" + classID + "|" + subjectID;
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return response;
    }

    public static String addStudentToClass(String className, String studentID) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        requset = "ADD_STUIDENT_TO_CLASS|" + className + "|" + studentID;
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return response;
    }

    public static ObservableList<String> getStudentsWithoutClass() {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String request = "GET_STUDENTS_WITHOUT_CLASS";
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        String[] userData = response.split("\\|");

        if (userData[0].equals("GET_STUDENTS_WITHOUT_CLASS_SUCCESS")) {
            ObservableList<String> observableStudentsData = FXCollections.observableArrayList();
            String studentsDataString = userData[1].substring(1, userData[1].length()-1);
            String[] studentsDataArray = studentsDataString.split("}, \\{");


            for (int i = 0; i < studentsDataArray.length; i++) {
                String studentDataString = studentsDataArray[i];
                 if (studentDataString.isEmpty()) {
                     return null;
                 }

                String[] studentAttributes = studentDataString.split(", ");
                StringBuilder studentData = new StringBuilder();


                for (String attribute : studentAttributes) {
                    String[] keyValue = attribute.split("=");
                    String attributeName = keyValue[0].trim();
                    String attributeValue = keyValue[1].trim();
                    studentData.append("\t").append(getAttributeString(attributeName, attributeValue));

                }
                if (studentData.charAt(studentData.length() - 1) == '}') {
                    studentData.deleteCharAt(studentData.length() - 1);
                }
                observableStudentsData.add(studentData.toString());
            }
            return observableStudentsData;
        }
        return null;
    }

    public static ObservableList<String> getAllFieldsFromClass(String tableName) {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String request = "GET_ALL_DATA_FROM_TABLE|"+ tableName;
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        if(!response.isEmpty()) {
            String[] items = response.split("\\|");

            ObservableList<String> listItems = FXCollections.observableArrayList();
            for (int i = 2; i < items.length; i++) {
                String[] itemParts = items[i].split(",");
                if (itemParts.length == 2) {
                    String id = itemParts[0];
                    String name = itemParts[1];
                    listItems.add("ID: " + id + "\tName: " + name);
                }
            }
            return listItems;
        }
        return null;
    }

    private static String getAttributeString(String attributeName, String attributeValue) {
        switch (attributeName) {
            case "uczen_id":
            case "{uczen_id":
                return "ID: " + attributeValue;
            case "imie":
                return "Name: " + attributeValue;
            case "nazwisko":
                return "Surname: " + attributeValue;
            case "telefon":
                return "Phone number: " + attributeValue;
            case "pesel":
                return "Pesel: " + attributeValue;
            case "email":
                return "E-mail: " + attributeValue;
            default:
                return "";
        }
    }

    public static boolean updateField(String tableName, String fieldName, String newValue, String conditionField, String conditionValue) {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String condition = conditionField + " = " + conditionValue;
        String request = "UPDATE_FIELDS|"+ tableName + "|" + fieldName + "|" + newValue + "|" + condition;
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        if(response.equals("UPDATE_FIELDS_SUCCESS")) {
            return true;
        }
        return false;
    }

    public static boolean deleteRecordById(String tableName, String id, String fieldName) {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String request = "DELETE_RECORD|"+ tableName + "|" + id + "|" + fieldName;
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        if(response.equals("DELETE_RECORD_SUCCESS")) {
            return true;
        }
        return false;
    }

    public static String getTableData(String tableName, String column, String condition) {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String request = "GET_TABLE_DATA|"+ tableName + "|" + column + "|" + condition;
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        if(response.equals("GET_TABLE_DATA_FAILURE")) {
            return null;
        } else {
            return response;
        }
    }

    public static String getAllFieldsFromTable(String tableName) {
        ServerConnection serverConnection = new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String request = "GET_ALL_DATA_FROM_TABLE|"+ tableName;
        String response = serverConnection.sendRequest(request);
        serverConnection.disconnect();

        if(response.equals("GET_TABLE_DATA_FAILURE")) {
            return null;
        } else {
            return response;
        }
    }

    public static String[] getStudentSubjectsAndGrades() {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();

        String request = "GET_STUDENT_SUBJECTS_AND_GRADES|" + LoginController.userID;
        String response = serverConnection.sendRequest(request);

        serverConnection.disconnect();

        String[] userData = response.split("\\|");

        if(userData[0].equals("GET_STUDENT_SUBJECTS_DATA_SUCCESS")) {
            return userData;
        }
        return new String[]{"GET_STUDENT_SUBJECTS_DATA_FAILURE"};
    }

    public static String getStudentByField(String field, String value) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();

        String request = "";
        if(field.equals("First Name")) {
            request = "GET_STUDENT_BY_NAME|" + value;
        } else if (field.equals("Last Name")) {
            request = "GET_STUDENT_BY_SURNAME|" + value;
        } else if (field.equals("Pesel")) {
            request = "GET_STUDENT_BY_PESEL|" + value;
        } else if (field.equals("E-mail")) {
            request = "GET_STUDENT_BY_EMAIL|" + value;
        } else if (field.equals("Phone Number")) {
            request = "GET_STUDENT_BY_PHONE_NUMBER|" + value;
        } else if (field.equals("Class")) {
            request = "GET_STUDENTS_FROM_CLASS|" + value;
        }

        String response = serverConnection.sendRequest(request);

        serverConnection.disconnect();

        return response;
    }
}
