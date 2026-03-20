module com.example.progettoispw {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.sql;

    requires mysql.connector.j;

    // Permette alla TableView (che sta in javafx.base) di leggere i tuoi Bean
    opens com.example.progettoispw.bean to javafx.base;

    opens com.example.progettoispw to javafx.fxml;
    exports com.example.progettoispw;
    //opens com.example.progettoispw.controller to javafx.fxml;
    //exports com.example.progettoispw.controller;

    opens com.example.progettoispw.controller.logic to javafx.fxml;
    exports com.example.progettoispw.controller.logic;

    opens com.example.progettoispw.controller.cli to javafx.fxml;
    exports com.example.progettoispw.controller.cli;

    exports com.example.progettoispw.controller.graphic;
    opens com.example.progettoispw.controller.graphic to javafx.fxml;
}