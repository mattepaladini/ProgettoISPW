package com.example.progettoispw.Controller.Graphic;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginGraphicController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    @FXML
    public void handleLogin(ActionEvent event) {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        // QUI ANDRA' LA LOGICA DI VERIFICA (Dummy per ora)
        if (!user.isEmpty() && !pass.isEmpty()) {
            System.out.println("Login tentato per: " + user);
            //SceneManager.changeScene("Home.fxml");
        } else {
            System.out.println("Inserisci credenziali!");
        }
    }

    @FXML
    public void goToRegistration(ActionEvent event) {
        try {
            // Attenzione al path, deve essere quello CORRETTO
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Registration.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
