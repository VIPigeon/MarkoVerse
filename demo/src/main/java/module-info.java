module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.json;


    opens example to javafx.fxml;
    exports example;
}