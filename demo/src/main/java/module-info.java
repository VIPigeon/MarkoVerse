module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens example to javafx.fxml;
    exports example;
}