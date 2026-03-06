package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.model.User;
import com.example.progettoispw.session.SessionManager;
import javafx.fxml.FXML;

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
