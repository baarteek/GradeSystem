module com.gradingsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires junit;
    requires org.apache.logging.log4j;

    opens com.gradingsystem to javafx.fxml;
    opens com.gradingsystem.controllers to javafx.fxml;
    exports com.gradingsystem;
    exports com.gradingsystem.controllers;
    exports com.gradingsystem.tests;
}