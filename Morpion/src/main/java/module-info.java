module com.example.td2 {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.td2 to javafx.fxml;
    exports com.example.td2;
}