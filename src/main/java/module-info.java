module uk.ac.aston.fyp {
    requires javafx.controls;
    requires javafx.fxml;


    opens uk.ac.aston.fyp to javafx.fxml;
    exports uk.ac.aston.fyp;
}