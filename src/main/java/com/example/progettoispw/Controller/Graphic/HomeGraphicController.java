package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;

import java.io.IOException;

public class HomeGraphicController {

    @FXML
    public void onSearchClick(ActionEvent event) {

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

    @FXML
    public void onProfileClick(ActionEvent event) throws IOException {

        // 1. RECUPERO L'UTENTE DALLA SESSIONE
        User currentUser = SessionManager.getInstance().getLoggedUser();

        String fxmlFile = "";
    try {

        // Recupero lo Stage (la finestra corrente)
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        // 2. CONTROLLO SE È LOGGATO
        if (currentUser == null) {
            // --- CASO UTENTE NON LOGGATO ---
            System.out.println("Utente non loggato. Carico il Login dentro il MainLayout.");

            // 1. Carico la CORNICE (MainLayout)
            FXMLLoader layoutLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = layoutLoader.load();

            // 2. Carico il CONTENUTO (Login)
            FXMLLoader loginLoader = new FXMLLoader(getClass().getResource("/GUI/Login.fxml")); // Controlla il path!
            Parent loginView = loginLoader.load();

            // 3. INSERISCO IL LOGIN AL CENTRO
            rootLayout.setCenter(loginView);

            // 4. Mostro la scena composta
            Scene scene = new Scene(rootLayout);
            // scene.getStylesheets().add(...) // Se hai un CSS globale
            stage.setScene(scene);

        }
            else {
                fxmlFile = (currentUser instanceof Seller) ? "/GUI/SellerProfile.fxml" : "/GUI/BuyerProfile.fxml";

                FXMLLoader layoutLoader = new FXMLLoader(getClass().getResource("/fxml/MainLayout.fxml"));
                BorderPane rootLayout = layoutLoader.load();

                FXMLLoader profileLoader = new FXMLLoader(getClass().getResource(fxmlFile));
                Parent profileView = profileLoader.load();

                rootLayout.setCenter(profileView);
                stage.setScene(new Scene(rootLayout));
            }


        // 3. CAMBIO SCENA
        stage.show();
    } catch (IOException e) {
        throw new RuntimeException(e);
    }
    }

    // --- Metodo Helper per evitare di riscrivere il codice di caricamento 100 volte ---
    private void changeScene(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Recupero lo Stage (la finestra) dal bottone cliccato
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);

            // (Opzionale) Aggiungo il CSS se necessario
            // scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento del file FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

}
