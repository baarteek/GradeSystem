package com.gradingsystem.utils;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;

public class DataPresenter {
    public static void loadStudentWithoutClassDataToListView(ListView listView) {
        ObservableList<String> studentsWithoutClass = UserDataProvider.getStudentsWithoutClass();
        if(studentsWithoutClass == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("There are no students with no classes assigned to them");
            alert.showAndWait();
        } else {
            listView.setItems(studentsWithoutClass);
        }
    }

    public static void loadClassesDataToListView(ListView listView) {
        ObservableList<String> listItems = UserDataProvider.getAllFieldsFromClass("klasa");
        if(listItems == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data");
            alert.showAndWait();
        }else {
            listView.setItems(listItems);
        }
    }

    public static void loadClassesNotLinkedWithSubjectDataToListView(ListView listView, String subjectID) {
        String condition = "klasa_id NOT IN (SELECT klasa_id FROM klasa_przedmiot WHERE przedmiot_id = " + subjectID + ")";
        String result = UserDataProvider.getTableData("klasa", "klasa_id, nazwa", condition);
        if(result == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data");
            alert.showAndWait();
        } else {
            String[] pairs = result.split("\\|");

            ObservableList<String> items = FXCollections.observableArrayList();
            for (String pair : pairs) {
                String[] parts = pair.split(",");
                String formatted = "ID: " + parts[0] + " \t Nazwa: " + parts[1];
                items.add(formatted);
            }
            listView.setItems(items);
        }

    }

    public static void loadClassesLinkedWithSubjectDataToListView(ListView listView, String subjectID) {
        String condition = "klasa_id IN (SELECT klasa_id FROM klasa_przedmiot WHERE przedmiot_id = " + subjectID + ")";
        String result = UserDataProvider.getTableData("klasa", "klasa_id, nazwa", condition);
        if(result == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data");
            alert.showAndWait();
        } else {
            String[] pairs = result.split("\\|");

            ObservableList<String> items = FXCollections.observableArrayList();
            for (String pair : pairs) {
                String[] parts = pair.split(",");
                String formatted = "ID: " + parts[0] + " \t Nazwa: " + parts[1];
                items.add(formatted);
            }
            listView.setItems(items);
        }

    }

    public static void loadSubjectsDataToLisView(ListView listView) {
        ObservableList<String> listItems = UserDataProvider.getAllFieldsFromClass("przedmiot");
        if(listItems == null) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText(null);
            alert.setContentText("No data");
            alert.showAndWait();
        }else {
            listView.setItems(listItems);
        }
    }

    public static String getIDFromString(String inputString) {
        String[] attributes = inputString.split("\t");
        for (String attribute : attributes) {
            String[] keyValue = attribute.split(":");
            if (keyValue[0].trim().equals("ID")) {
                return keyValue[1].trim();
            }
        }
        return "No ID found";
    }
}
