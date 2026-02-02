package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Controller.Logic.AuthController;
import com.example.progettoispw.Controller.Logic.RegistrationController;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.exception.invalidInputException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginGraphicController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final Logger logger = Logger.getLogger(LoginGraphicController.class.getName());

    @FXML
    public void StartLogin(ActionEvent event) throws IOException {
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (!user.isEmpty() && !pass.isEmpty()) {
            UserBean userBean = new UserBean(user, pass);

            AuthController authController = new AuthController();
            authController.checkUserExist(userBean);

            logger.log(Level.INFO, "User " + user + " logged in");

        } else {
            throw new invalidInputException("Username e/o password mancanti");
        }

        //carico schermata Home

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
        BorderPane rootLayout = mainLoader.load();

        // B. Carico il contenuto centrale (Home)
        FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/GUI/Home.fxml"));
        Node homeNode = homeLoader.load();

        // C. Metto la Home al centro
        rootLayout.setCenter(homeNode);

        // D. Mostro la scena
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        // Usa le dimensioni che preferisci o quelle correnti
        Scene scene = new Scene(rootLayout, 800, 600);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    public void goToRegistration(ActionEvent event) {
        try {
            // Attenzione al path, deve essere quello CORRETTO
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Registration.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }
}
