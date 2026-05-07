package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.pattern.observer.NotificationObserver;
import com.example.progettoispw.pattern.observer.NotificationSubject;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable, NotificationObserver {


    @FXML
    private Button btnVendi;
    @FXML
    private Button btnCompra;
    @FXML
    private Label lblNotificationBadge;

    private final SceneManager sceneManager = new SceneManager();

    @FXML
    public void doLogout() {
        NotificationSubject.getInstance().detach(this);
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
            lblNotificationBadge.setVisible(false);
            lblNotificationBadge.setText("");
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
        lblNotificationBadge.setVisible(false);

        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser != null) {
            if (currentUser.getTipoUtente().equals(UserType.SELLER)) {
                btnVendi.setVisible(true);
            } else if (currentUser.getTipoUtente().equals(UserType.BUYER)) {
                btnCompra.setVisible(true);

                //only for buyer
                NotificationSubject.getInstance().attach(this);
            }

        }

    }

    @Override
    public void onNotficationReceived(String message) {
        Platform.runLater(() -> {
            lblNotificationBadge.setVisible(true);
            int current = lblNotificationBadge.getText().isBlank()
                    ? 0
                    : Integer.parseInt(lblNotificationBadge.getText());
            lblNotificationBadge.setText(String.valueOf(current + 1));
        });
    }
}

