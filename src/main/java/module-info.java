module com.example.librarymanager {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.librarymanager to javafx.fxml;
    exports com.example.librarymanager;
}