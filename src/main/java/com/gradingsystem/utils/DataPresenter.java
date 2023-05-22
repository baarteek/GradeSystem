package com.gradingsystem.utils;

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
