module com.example.vinylcollector {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.vinylcollector to javafx.fxml;
    exports com.example.vinylcollector;
}