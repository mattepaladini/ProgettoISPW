package com.example.progettoispw.Controller.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.event.ActionEvent;
import java.io.IOException;

public class RegistrationGraphicController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkIsVenditore;

    @FXML
    public void onRegisterClick(ActionEvent event) {
        // 1. Recupero i dati

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean isVenditore = chkIsVenditore.isSelected();

        // 2. Controllo banale (giusto per non mandare null)
        if(username.isEmpty() || password.isEmpty()) {
            System.out.println("Errore: Compila tutti i campi!");
            return;
        }

        System.out.println("REGISTRAZIONE IN CORSO...");
        System.out.println("Utente: " + username);
        System.out.println("Ruolo Venditore: " + isVenditore);

        // TODO: Qui chiamerai il tuo RegistrationController (Logic)
        // logicController.registraUtente(bean);

        cambiaScena(event, "/com/example/progettoispw/GUI/Home.fxml");
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        // Questo metodo serve per tornare alla schermata di Login
        cambiaScena(event, "/com/example/progettoispw/GUI/Login.fxml");
    }

    // Metodo helper per cambiare pagina
    private void cambiaScena(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent root = loader.load();

            // Recupero lo Stage (la finestra) dal bottone che è stato cliccato
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
