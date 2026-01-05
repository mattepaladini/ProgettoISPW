package com.example.progettoispw.Controller.Graphic;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeGraphicController {

    @FXML
    public void onSearchClick(ActionEvent event) {
/*
            // 1. Carica la schermata Search.fxml
            // Assicurati che il percorso sia giusto!
            //FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/Search.fxml"));
            //Parent searchView = loader.load();

            // 2. RECUPERA IL "PADRE" (Il MainLayout che è già aperto a video)
            // Risaliamo dal bottone cliccato fino alla radice della finestra
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            BorderPane mainLayout = (BorderPane) source.getScene().getRoot();

            try {
                //
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Search.fxml"));
                Parent searchView = loader.load();

                // Imposta la nuova vista al centro del BorderPane padre
                mainLayout.setCenter(searchView);
        } catch (IOException e) {
                e.printStackTrace();

        }

 */

        try {
            // 1. Carica la Search
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Search.fxml"));
            Parent searchRoot = loader.load();

            // (Opzionale) Passaggio dati/controller se serve
            // SearchGraphicController ctrl = loader.getController();
            // ctrl.setLogicController(...);

            // 2. Recupera lo Stage
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. SOSTITUISCI TUTTO (Niente più setCenter)
            stage.setScene(new Scene(searchRoot));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
