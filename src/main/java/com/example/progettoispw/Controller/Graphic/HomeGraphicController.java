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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/MainLayout.fxml"));
            Parent searchView = loader.load();

            // 2. Recupera il "Padre" di tutto (il MainLayout che contiene la NavBar)
            // ((Node) event.getSource()) prende il bottone cliccato
            // .getScene().getRoot() risale fino alla radice della finestra (che è il BorderPane)
            BorderPane mainLayout = (BorderPane) ((Node) event.getSource()).getScene().getRoot();

            // 3. Recupera l'area centrale (che è uno StackPane, come abbiamo impostato nel MainLayout)
            StackPane centerArea = (StackPane) mainLayout.getCenter();

            // 4. Sostituisce il contenuto
            centerArea.getChildren().clear();   // Toglie la Home
            centerArea.getChildren().add(searchView); // Mette la Search

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore: Non trovo il file Search.fxml!");
        }
    }

}
