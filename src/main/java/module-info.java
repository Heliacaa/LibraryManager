module com.example.librarymanager {
    requires com.fasterxml.jackson.databind;
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarymanager to javafx.fxml;
    exports com.example.librarymanager;
}