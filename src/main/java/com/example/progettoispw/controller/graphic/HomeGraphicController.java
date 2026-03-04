package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.session.SessionManager;
import com.example.progettoispw.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.example.progettoispw.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable {


    @FXML
    private Button btnVendi;
    @FXML
    private Button btnCompra;
    @FXML
    private Button btnCarrello;

    private final SceneManager sceneManager = new SceneManager();

    @FXML
    public void doLogout() {
        SessionManager.getInstance().logout();
    }


    @FXML
    public void onSearchClick(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/Search.fxml");

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

        sceneManager.startScene(event, fxmlFile);

    }

    @FXML
    public void onSellPageClicked(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/SellerCatalog.fxml");

    }

    @FXML
    public void onBuyCards(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/SellerCatalog.fxml");

    }

    @FXML
    public void onCartClick(ActionEvent event) {


        String fxmlFile = "";
        if (SessionManager.getInstance().getLoggedUser() == null) {

            //mando l'utente alla schermata Login
            fxmlFile = "/GUI/Login.fxml";
        } else {
            fxmlFile = "/GUI/Cart.fxml";
        }

        sceneManager.startScene(event, fxmlFile);


    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnVendi.setVisible(false);
        btnCompra.setVisible(false);
        btnCarrello.setVisible(false);

        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser != null) {
            if (currentUser.getTipoUtente().equals(UserType.SELLER)) {
                btnVendi.setVisible(true);
            } else if (currentUser.getTipoUtente().equals(UserType.BUYER)) {
                btnCompra.setVisible(true);
                btnCarrello.setVisible(true);
            }

        }

    }

}

