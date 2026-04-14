package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.NotificationBean;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class NotificationsGraphicController implements Initializable {

    private static final SceneManager sceneManager = new SceneManager();

    @FXML
    private ListView<NotificationBean> notificationsList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Diciamo alla ListView come disegnare ogni riga
        notificationsList.setCellFactory(param -> new ListCell<NotificationBean>() { // Usa il tipo corretto

            // Creiamo gli elementi grafici della riga
            private final HBox container = new HBox(10);
            private final Label textLabel = new Label();
            private final Region spacer = new Region();
            private final Button readButton = new Button("✔ Letti");

            {
                // Configurazione layout della riga
                HBox.setHgrow(spacer, Priority.ALWAYS); // Spinge il bottone tutto a destra
                container.setAlignment(Pos.CENTER_LEFT);
                readButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #2ecc71; -fx-cursor: hand; -fx-font-weight: bold;");

                container.getChildren().addAll(textLabel, spacer, readButton);

                // Cosa succede quando clicchi "Letti"
                readButton.setOnAction(event -> {
                    NotificationBean notificaSelezionata = getItem();

                    ManageNotificationsController manageNotificationsController = new ManageNotificationsController();
                    manageNotificationsController.markAsRead(notificaSelezionata.getId());

                    // 2. Rimuove visivamente la riga dalla lista
                    getListView().getItems().remove(notificaSelezionata);
                });
            }

            // Questo metodo riempie i dati quando la riga viene mostrata
            @Override
            protected void updateItem(NotificationBean item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setGraphic(null); // Riga vuota
                } else {
                    String displayText = "[" + item.getDate() + "] " + item.getMessage();
                    textLabel.setText(displayText); // Se usi un Bean sarà item.getMessaggio()
                    setGraphic(container);   // Mostra l'HBox con Label e Bottone
                }
            }
        });



        // Questo metodo parte in automatico quando si carica l'FXML
        loadNotifications();
    }
    private void loadNotifications() {
        try {
            User currentUser = SessionManager.getInstance().getLoggedUser();
            ManageNotificationsController logicController = new ManageNotificationsController();

            // Recuperi la lista di fagioli (beans) dal DB
            List<NotificationBean> notifications = logicController.getUnreadNotifications(currentUser.getUsername());

            // La passi DIRETTAMENTE alla ListView in una sola riga!
            notificationsList.getItems().addAll(notifications);

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

    @FXML
    public void onMarkClick(ActionEvent event) {
        ManageNotificationsController logicController = new ManageNotificationsController();
        logicController.markAsRead(notificationsList.getSelectionModel().getSelectedIndex());

        ToastManager.showToast((Stage) notificationsList.getScene().getWindow(), "Notifica letta");
    }
}
