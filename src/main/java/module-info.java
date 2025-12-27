module com.example.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.progettoispw to javafx.fxml;
    exports com.example.progettoispw;
    exports com.example.progettoispw.Controller;
    opens com.example.progettoispw.Controller to javafx.fxml;
    exports com.example.progettoispw.Controller.Graphic;
    opens com.example.progettoispw.Controller.Graphic to javafx.fxml;
}