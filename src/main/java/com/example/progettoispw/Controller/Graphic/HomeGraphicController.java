package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.Buyer;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable {


    @FXML
    private Button btnVendi;
    @FXML
    private Button btnCompra;

    @FXML
    public void doLogout() {
        SessionManager.getInstance().logout();
    }


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
        if (currentUser == null) {
            fxmlFile = "/GUI/Login.fxml";
        } else {

            SessionManager.getInstance().logout();
            System.out.println("Utente uscito");

            fxmlFile = "/GUI/Home.fxml";
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della vista: " + fxmlFile);
            e.printStackTrace();
        }



    }

    @FXML
    public void onSellPageClicked(ActionEvent event) {
        try {
            // 1. Carico la CORNICE (MainLayout)
            // Assicurati che il path sia corretto
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // 2. Carico il CONTENUTO (SellerCatalog)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/GUI/SellerCatalog.fxml"));
            // Uso 'Node' perché il contenuto è la root del file FXML (es. VBox o AnchorPane)
            Node catalogNode = contentLoader.load();

            // 3. INIEZIONE: Metto il catalogo al CENTRO del MainLayout
            rootLayout.setCenter(catalogNode);

            // 4. Mostro la scena combinata
            // Mantengo le dimensioni fisse per evitare il restringimento della finestra
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            // Opzionale: carico il CSS se serve
            // scene.getStylesheets().add(getClass().getResource("/css/light-theme.css").toExternalForm());

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            System.err.println("Errore nel caricamento della pagina SellerCatalog.");
            e.printStackTrace();
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionManager.getInstance().getLoggedUser();


        btnVendi.setVisible(false);
        btnCompra.setVisible(false);

        User currentUser = SessionManager.getInstance().getLoggedUser();
        if(currentUser != null) {
            if(currentUser.getTipoUtente().equals(UserType.SELLER)) {
                btnVendi.setVisible(true);
            } else if(currentUser.getTipoUtente().equals(UserType.BUYER)) {
                btnCompra.setVisible(true);
            }

        }

    }


    }

