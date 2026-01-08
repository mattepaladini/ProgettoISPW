package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Session.SessionManager;
import javafx.fxml.FXML;
import com.example.progettoispw.model.User;

import java.awt.*;

public class SellerProfileGraphicController {
    @FXML
    private Label lblWelcome;

    // Chiama questo metodo SUBITO dopo il caricamento
    public void setupProfile() {
        // Recupera l'utente dalla sessione (non serve passarlo come parametro!)
        User user = SessionManager.getInstance().getLoggedUser();

        if (user != null) {
            lblWelcome.setText("Bentornato, " + user.getUsername());
        }
    }
}
