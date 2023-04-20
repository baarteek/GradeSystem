module com.gradingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.gradingsystem to javafx.fxml;
    opens com.gradingsystem.controllers to javafx.fxml;
    exports com.gradingsystem;
    exports com.gradingsystem.controllers;
}