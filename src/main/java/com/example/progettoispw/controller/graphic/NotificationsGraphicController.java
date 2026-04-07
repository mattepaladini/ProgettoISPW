package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.NotificationBean;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationsGraphicController implements Initializable {

    private static final SceneManager sceneManager = new SceneManager();

    @FXML
    private ListView<String> notificationsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Questo metodo parte in automatico quando si carica l'FXML
        loadNotifications();
    }

    private void loadNotifications() {
        try {

            User currentUser = SessionManager.getInstance().getLoggedUser();

            ManageNotificationsController logicController = new ManageNotificationsController();

            List<NotificationBean> notifications = logicController.getUnreadNotifications(currentUser.getUsername());


            for (NotificationBean bean : notifications) {

                String displayText = "[" + bean.getDate() + "] " + bean.getMessage();
                notificationsList.getItems().add(displayText);
            }

        } catch (Exception e) {

            Stage currentStage = (Stage) notificationsList.getScene().getWindow();
            ToastManager.showErrorToast(currentStage, "Impossibile caricare le notifiche.");
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneManager.startScene(event,"/GUI/Home.fxml");
    }

    @FXML
    public void onLogoutClick(ActionEvent event) {
        SessionManager.getInstance().logout();
        sceneManager.startScene(event,"/GUI/Home.fxml");
    }
}
