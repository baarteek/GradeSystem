module com.gradingsystem {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens com.gradingsystem to javafx.fxml;
    exports com.gradingsystem;
}