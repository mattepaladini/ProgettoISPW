package com.example.progettoispw.Controller.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import java.io.IOException;

public class MainLayoutController {

    // Riferimento all'area centrale del BorderPane
    @FXML
    private StackPane contentArea;

    // Metodo che viene chiamato appena il layout è caricato
    @FXML
    public void initialize() {
        // Appena apro l'app, carico subito la Home
        loadPage("/com/example/progettoispw/GUI/Home.fxml");
    }

    private void navigateTo(ActionEvent event, String fxmlPath) {
        try {
            // 1. Carico la nuova vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();

            // 2. Risalgo al BorderPane del MainLayout
            Node source = (Node) event.getSource();
            // Risalgo fino alla root della scena (che è il BorderPane del MainLayout)
            BorderPane mainLayout = (BorderPane) source.getScene().getRoot();

            // 3. Prendo il centro (StackPane) e sostituisco il contenuto
            StackPane centerPane = (StackPane) mainLayout.getCenter();
            centerPane.getChildren().clear();
            centerPane.getChildren().add(newView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Errore: Impossibile caricare " + fxmlPath);
        }
    }

    // --- AZIONI DELLA NAVBAR ---

    @FXML
    public void goToSearch(ActionEvent event) {
        // Qui ci colleghiamo finalmente a Search.fxml (che avrà la barra di ricerca)
        navigateTo(event, "/com/example/progettoispw/GUI/Search.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadPage("/com/example/progettoispw/GUI/Home.fxml");
    }

    @FXML
    public void showBuy(ActionEvent event) {
        //loadPage("/fxml/BuyCards.fxml");
    }

    @FXML
    public void showSell(ActionEvent event) {
        //loadPage("/fxml/SellCards.fxml");
    }

    @FXML
    public void showProfile(ActionEvent event) {
        //loadPage("/fxml/Profile.fxml");
    }

    @FXML
    public void doLogout(ActionEvent event) {
        // Qui dovresti cambiare l'intera Scena per tornare al Login
        // Perché il Login non ha la navbar!
        System.out.println("Logout effettuato");
    }

    // --- METODO UTILITY PER CARICARE LE PAGINE ---

    private void loadPage(String fxmlPath) {
        try {
            // 1. Carico il file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();

            // 2. Pulisco l'area centrale
            contentArea.getChildren().clear();

            // 3. Aggiungo la nuova vista
            contentArea.getChildren().add(newView);

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare la vista: " + fxmlPath);
        }
    }

}
