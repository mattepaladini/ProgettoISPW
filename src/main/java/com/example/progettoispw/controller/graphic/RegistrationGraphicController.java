package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.RegistrationController;
import com.example.progettoispw.exception.LoadPageException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import static com.example.progettoispw.model.UserType.BUYER;
import static com.example.progettoispw.model.UserType.SELLER;

public class RegistrationGraphicController {

    @FXML private TextField txtUsername;
    @FXML private PasswordField txtPassword;
    @FXML private CheckBox chkIsVenditore;

    private static final SceneManager sceneManager = new SceneManager();

    @FXML
    public void onRegisterClick(ActionEvent event)  {
        // 1. Recupero i dati

        String username = txtUsername.getText();
        String password = txtPassword.getText();
        boolean isVenditore = chkIsVenditore.isSelected();

        UserBean userbean = new UserBean(username, password);

        //Utenza di default --> BUYER
        if(isVenditore){
            userbean.setUsertype(SELLER);
        }else{
            userbean.setUsertype(BUYER);
        }

        RegistrationController regiController = new RegistrationController();
        regiController.completeRegistration(userbean);

        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root, 800, 600));
            stage.show();
        }catch(IOException e){
            ErrorHandler.show(new LoadPageException("Impossibile caricare la pagina"));
        }

    }

    @FXML
    public void onBackClick(ActionEvent event) {
        // Questo metodo serve per tornare alla schermata di Login
        sceneManager.startScene(event, "/GUI/Login.fxml");
    }

}
