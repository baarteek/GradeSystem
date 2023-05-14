package com.gradingsystem.utils;

import com.gradingsystem.controllers.LoginController;
import javafx.scene.control.Alert;

public class UserDataProvider {
    public static String[] getUserData(String tableName, int userID) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        if(tableName.equals("nauczyciel")) {
            requset = "GET_USER_DATA|TEACHER|" + LoginController.userID;
        } else if(tableName.equals("uczen")) {
            requset = "GET_USER_DATA|STUDENT|" + LoginController.userID;
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
        }
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();

        return response;
    }

    public static boolean isDataInDatabase(String tableName, String column, String value) {
        ServerConnection serverConnection= new ServerConnection("localhost", 1025);
        serverConnection.connect();
        String requset = new String();
        if(tableName.equals("nauczyciel")) {
            requset = "CHECK_DATA_EXISTT|TEACHER|" + column +  "|" + value;
        } else if(tableName.equals("uczen")) {
            requset = "CHECK_DATA_EXISTT|STUDENT|" + column + "|"  + value;
        }
        String response = serverConnection.sendRequest(requset);
        serverConnection.disconnect();
        return !response.equals("SUCCESS");
    }
}
