package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.AuthController;
import com.example.progettoispw.exception.invalidInputException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.util.logging.Level;
import java.util.logging.Logger;


public class LoginGraphicController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    private static final Logger logger = Logger.getLogger(LoginGraphicController.class.getName());
    private static final SceneManager sceneManager = new SceneManager();

    @FXML
    public void startLogin(ActionEvent event){
        String user = usernameField.getText();
        String pass = passwordField.getText();

        if (!user.isEmpty() && !pass.isEmpty()) {
            UserBean userBean = new UserBean(user, pass);

            AuthController authController = new AuthController();
            authController.checkUserExist(userBean);

            logger.log(Level.INFO, "User {0} loggato" ,user);

        } else {
            throw new invalidInputException("Username e/o password mancanti");
        }

        //carico schermata Home

        sceneManager.startScene(event,"/GUI/Home.fxml");

    }

    @FXML
    public void goToRegistration(ActionEvent event) {

        sceneManager.startScene(event,"/GUI/Registration.fxml");

    }
}
