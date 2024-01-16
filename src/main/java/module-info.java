module com.example.vinylcollector {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;

    opens com.example.vinylcollector to javafx.fxml;
    exports com.example.vinylcollector;
}