package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable {


    @FXML
    private Button btnVendi;
    @FXML
    private Button btnCompra;

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
    public void onProfileClick(ActionEvent event) {

        User currentUser = SessionManager.getInstance().getLoggedUser();

        String fxmlFile = "";
        if (currentUser == null) {
            fxmlFile = "/GUI/Login.fxml";
        } else {

            fxmlFile = "/GUI/Notifications.fxml";
        }

        sceneManager.startScene(event, fxmlFile);

    }

    @FXML
    public void onSellPageClicked(ActionEvent event) {

        String fxmlFile = "/GUI/SellerCatalog.fxml";
        sceneManager.startScene(event, fxmlFile);

    }

    @FXML
    public void onBuyCards(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/Cart.fxml");

    }

    @FXML
    public void onCartClick(ActionEvent event) {
        String fxmlFile = "/GUI/Cart.fxml";
        sceneManager.startScene(event, fxmlFile);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        btnVendi.setVisible(false);
        btnCompra.setVisible(false);

        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser != null) {
            if (currentUser.getTipoUtente().equals(UserType.SELLER)) {
                btnVendi.setVisible(true);
            } else if (currentUser.getTipoUtente().equals(UserType.BUYER)) {
                btnCompra.setVisible(true);
            }

        }

    }

}

