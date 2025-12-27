package com.example.progettoispw.Controller.Graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HomeGraphicController {

    @FXML
    public void onSearchClick(ActionEvent event) {
        try {
            // 1. Carica la schermata Search.fxml
            // Assicurati che il percorso sia giusto!
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/Search.fxml"));
            Parent searchView = loader.load();

            // 2. RECUPERA IL "PADRE" (Il MainLayout che è già aperto a video)
            // Risaliamo dal bottone cliccato fino alla radice della finestra
            Node source = (Node) event.getSource();
            BorderPane mainLayout = (BorderPane) source.getScene().getRoot();

            // 3. SOSTITUISCI IL CENTRO
            // Cerchiamo lo StackPane centrale che avevamo messo nel MainLayout
            StackPane centerPane = (StackPane) mainLayout.getCenter();

            // Puliamo e mettiamo la nuova vista
            centerPane.getChildren().clear();
            centerPane.getChildren().add(searchView);

            Node sideBar = mainLayout.getLeft();

            if (sideBar != null) {
                sideBar.setVisible(true);
                sideBar.setManaged(true); // Gli restituisco lo spazio fisico
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore: Non trovo il file Search.fxml!");
        }
    }

}
