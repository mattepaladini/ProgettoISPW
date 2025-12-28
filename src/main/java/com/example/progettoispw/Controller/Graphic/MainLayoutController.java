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

    // Mi servono per oscurare i bottoni relativi alla pagina in cui mi trovo
    @FXML private Button btnHome;
    @FXML private Button btnSearch;
    @FXML private Button btnSell;
    @FXML private Button btnProfile;


    // Metodo che viene chiamato appena il layout è caricato
    @FXML
    public void initialize() {

        if (centerPane == null) {
            System.err.println("❌ ERRORE GRAVE: 'centerPane' è NULL!");
            System.err.println("Verifica in MainLayout.fxml di aver scritto: <StackPane fx:id=\"centerPane\" ...>");
            return; // Esco per evitare il crash
        }

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
            centerPane.getChildren().clear();

            // 3. Aggiungo la nuova vista
            centerPane.getChildren().add(newView);

            // --- TRUCCO PER NASCONDERE LA NAVBAR ---

            if (fxmlPath.equals("/com/example/progettoispw/GUI/Home.fxml")) {
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
        resetButton(btnHome);
        resetButton(btnSearch);
        resetButton(btnSell);
        resetButton(btnProfile);

        // 2. NASCONDI: Spegniamo solo quello della pagina corrente
        switch (currentPath) {
            case "/fxml/Search.fxml":
                hideButton(btnSearch);
                break;
            case "/fxml/Sell.fxml":
                hideButton(btnSell);
                break;
            case "/fxml/Profile.fxml":
                hideButton(btnProfile);
                break;
            // Aggiungi altri casi se hai altre pagine
        }
    }

    // Helper per mostrare un bottone
    private void resetButton(Button btn) {
        if (btn != null) {
            btn.setVisible(true);
            //btn.setManaged(true); // Occupa spazio
        }
    }

    // Helper per nascondere un bottone
    private void hideButton(Button btn) {
        if (btn != null) {
            btn.setVisible(false);
            //btn.setManaged(false); // Collassa lo spazio (non lascia il buco vuoto)
        }
    }



}
