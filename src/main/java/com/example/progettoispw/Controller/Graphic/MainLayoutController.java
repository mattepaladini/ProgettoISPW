package com.example.progettoispw.Controller.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.io.IOException;

public class MainLayoutController {

    // Riferimento all'area centrale del BorderPane
    @FXML
    private StackPane centerPane;

    @FXML
    private VBox sideBar;

    @FXML private BorderPane borderPane;

    // Mi servono per oscurare i bottoni relativi alla pagina in cui mi trovo
    @FXML private javafx.scene.control.Button btnHome;
    @FXML private javafx.scene.control.Button btnSearch;
    @FXML private javafx.scene.control.Button btnSell;
    @FXML private javafx.scene.control.Button btnProfile;

    // Metodo riutilizzabile per cambiare il centro della pagina
    public void setCenterContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();
            borderPane.setCenter(newView);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare la vista: " + fxmlPath);
        }
    }

    // Metodo che viene chiamato appena il layout è caricato
    @FXML
    public void initialize() {

        if (centerPane == null) {
            System.err.println("❌ ERRORE GRAVE: 'centerPane' è NULL!");
            System.err.println("Verifica in MainLayout.fxml di aver scritto: <StackPane fx:id=\"centerPane\" ...>");
            return; // Esco per evitare il crash
        }

        // Appena apro l'app, carico subito la Home
        loadPage("/GUI/Home.fxml");
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
        //navigateTo(event, "/GUI/Search.fxml");
        loadPage("/GUI/Search.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadPage("/GUI/Home.fxml");
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
            centerPane.getChildren().clear();

            // 3. Aggiungo la nuova vista
            centerPane.getChildren().add(newView);

            // --- TRUCCO PER NASCONDERE LA NAVBAR ---

            if (fxmlPath.equals("/GUI/Home.fxml")) {
                // SE È LA HOME: Nascondi la barra
                sideBar.setVisible(false);
                sideBar.setManaged(false); // Questo fa "collassare" lo spazio, così la Home si allarga
            } else {
                // SE È UN'ALTRA PAGINA: Mostra la barra
                sideBar.setVisible(true);
                sideBar.setManaged(true);

                updateSidebarButtons(fxmlPath);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Impossibile caricare la vista: " + fxmlPath);
        }
    }

    /**
     * Metodo che gestisce quale bottone nascondere
     */
    private void updateSidebarButtons(String currentPath) {
        // 1. RESET: Prima rendiamo TUTTI i bottoni visibili


        // 2. NASCONDI: Spegniamo solo quello della pagina corrente
        switch (currentPath) {
            case "/GUI/Search.fxml":
                btnSearch.setVisible(false);
                break;
            case "/GUI/Sell.fxml":
                btnSell.setVisible(false);
                break;
            case "/GUI/Profile.fxml":
                btnProfile.setVisible(false);
                break;
            // Aggiungi altri casi se hai altre pagine
        }
    }


}
