package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import model.Customer;
import model.Seller;
import model.User;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable {

    @FXML private Button btnCompra;
    @FXML private Button btnVendi;

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
    /*try {

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



        // 3. CAMBIO SCENA
        stage.show();
    } catch (IOException e) {
        throw new RuntimeException(e);
    } */
        if (currentUser == null) {
            fxmlFile = "/GUI/Login.fxml";
        }

        // 2. Eseguo il caricamento "Cornice + Contenuto"
        try {
            // A. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // B. Carico il CONTENUTO (Login o Profilo)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            // Uso 'Node' perché il contenuto potrebbe essere VBox, AnchorPane o altro
            Node contentNode = contentLoader.load();

            // C. INIEZIONE: Metto il contenuto al centro della cornice
            rootLayout.setCenter(contentNode);

            // D. Preparo la scena
            // Manteniamo le dimensioni fisse come ci siamo detti
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della vista: " + fxmlFile);
            e.printStackTrace();
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionManager.getInstance().getLoggedUser();

        if(user==null){
            btnVendi.setVisible(false);
            btnCompra.setVisible(false);
        } else {


            if (user instanceof Seller) {
                // --- È UN VENDITORE ---
                // Può Vendere, NON può Comprare

                btnVendi.setVisible(true);
                btnCompra.setVisible(false);

            } else if (user instanceof Customer) {

                // --- È UN COMPRATORE ---
                // Può Comprare, NON può Vendere

                btnVendi.setVisible(false);
                btnCompra.setVisible(true);
            }
            else{
                System.out.println("Errore");
            }


        }


    }
}
