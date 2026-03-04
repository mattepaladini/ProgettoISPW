package com.example.progettoispw.controller.Graphic;

import com.example.progettoispw.controller.Logic.RegistrationController;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.exception.invalidInputException;
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
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.example.progettoispw.model.UserType.BUYER;
import static com.example.progettoispw.model.UserType.SELLER;

public class RegistrationGraphicController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkIsVenditore;

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    @FXML
    public void onRegisterClick(ActionEvent event) throws IOException {
        // 1. Recupero i dati

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean isVenditore = chkIsVenditore.isSelected();

        // 2. Controllo banale (giusto per non mandare null)
        if(username.isEmpty() || password.isEmpty()) {
            throw new invalidInputException("Username e/o password mancanti");
        }

        /*
        System.out.println("REGISTRAZIONE IN CORSO...");
        System.out.println("Utente: " + username);
        System.out.println("Ruolo Venditore: " + isVenditore);
        */


        UserBean userbean = new UserBean(username, password);

        //Utenza di default --> CUSTOMER
        if(isVenditore){
            userbean.setUsertype(SELLER);
        }else{
            userbean.setUsertype(BUYER);
        }

        RegistrationController regiController = new RegistrationController();
        regiController.completeRegistration(userbean);

        // 3. CAMBIO SCENA -> VADO ALLA HOME (MainLayout)
        // Poiché la sessione è piena, HomeGraphicController mostrerà i tasti giusti.
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
        Parent root = loader.load();

        // ... (codice per caricare la Home al centro del MainLayout) ...

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root, 800, 600));
        stage.show();
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        // Questo metodo serve per tornare alla schermata di Login
        cambiaScena(event, "/GUI/Login.fxml");
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
            logger.log(Level.WARNING,e.getMessage());
        }
    }
}
