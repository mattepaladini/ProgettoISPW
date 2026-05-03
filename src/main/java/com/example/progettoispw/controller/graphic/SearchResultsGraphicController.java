package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCartController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.LoadPageException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchResultsGraphicController {

    private static final Logger logger = Logger.getLogger(SearchResultsGraphicController.class.getName());

    @FXML
    private VBox resultsContainer; // Il contenitore dentro lo ScrollPane

    @FXML
    private Label lblMessage;

    @FXML
    private Button btnAddToCart;

    @FXML
    private TableView<CollectableCardBean> resultsTable;

    @FXML
    private TableColumn<CollectableCardBean, String> colNome;

    @FXML
    private TableColumn<CollectableCardBean, Float> colPrezzo;

    @FXML
    private TableColumn<CollectableCardBean, String> colGradazione;

    @FXML
    private TableColumn<CollectableCardBean, String> colVenditore;

    @FXML
    private TableColumn<CollectableCardBean, CollectableCardBean> colSegui;

    private ManageNotificationsController notificationsController= new ManageNotificationsController();

    private SceneManager sceneManager = new SceneManager();

    @FXML
    public void initialize() {
        // Disabilito il bottone per aggiungere al carrello finché non viene selezionata la riga
        btnAddToCart.disableProperty().bind(resultsTable.getSelectionModel().selectedItemProperty().isNull());

        setupTableColumns();
    }

    // 1. ESTRAZIONE: Configurazione generale delle colonne
    private void setupTableColumns() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCarta"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoCorrente"));
        colGradazione.setCellValueFactory(new PropertyValueFactory<>("gradazione"));
        colVenditore.setCellValueFactory(new PropertyValueFactory<>("venditore"));

        colSegui.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colSegui.setCellFactory(param -> createFollowCell());
    }

    // 2. ESTRAZIONE: Creazione della cella
    private TableCell<CollectableCardBean, CollectableCardBean> createFollowCell() {
        return new TableCell<>() {
            private final Button btnSegui = new Button("❤ Segui");

            {
                btnSegui.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-cursor: hand;");
                btnSegui.setOnAction(event -> handleFollowAction(btnSegui, getTableView().getItems().get(getIndex())));
            }

            @Override
            protected void updateItem(CollectableCardBean item, boolean empty) {
                super.updateItem(item, empty);

                // Early Exit per le celle vuote
                if (empty || item == null) {
                    setGraphic(null);
                    return;
                }

                updateFollowButtonUI(btnSegui, item);
                setGraphic(btnSegui);
            }
        };
    }

    // 3. ESTRAZIONE: Logica del click sul bottone
    private void handleFollowAction(Button btnSegui, CollectableCardBean cardSelezionata) {
        User loggedUser = SessionManager.getInstance().getLoggedUser();
        Stage stage = (Stage) btnSegui.getScene().getWindow();

        // Guard Clause: controllo utente loggato
        if (loggedUser == null) {
            ToastManager.showErrorToast(stage, "Devi fare il login per seguire un venditore!");
            return;
        }

        String sellerUsername = cardSelezionata.getSeller();

        try {
            boolean isFollowed = notificationsController.followSeller(loggedUser.getUsername(), sellerUsername);
            if (isFollowed) {
                btnSegui.setText("✔️ Seguito");
                btnSegui.setDisable(true);
                ToastManager.showToast(stage, "Ora segui " + sellerUsername + "!");
            }
        } catch (Exception e) {
            logger.log(Level.SEVERE, "Errore durante il follow", e);
            ToastManager.showErrorToast(stage, "Errore di connessione.");
        }
    }

    // 4. ESTRAZIONE: Logica di aggiornamento visivo del bottone
    private void updateFollowButtonUI(Button btnSegui, CollectableCardBean item) {
        User loggedUser = SessionManager.getInstance().getLoggedUser();

        // Fix di sicurezza: se nessun utente è loggato, nascondo il bottone
        if (loggedUser == null) {
            btnSegui.setVisible(false);
            return;
        }

        String currentUser = loggedUser.getUsername();

        // Guard Clause: l'utente è il venditore stesso
        if (currentUser.equals(item.getSeller())) {
            btnSegui.setVisible(false);
            btnSegui.setManaged(false); // Nasconde lo spazio vuoto
            return;
        }

        // Resetto la visibilità per le righe normali
        btnSegui.setVisible(true);
        btnSegui.setManaged(true);

        boolean isAlreadyFollowing = notificationsController.checkFollowStatus(currentUser, item.getSeller());

        if (isAlreadyFollowing) {
            btnSegui.setText("✔ Segui già");
            btnSegui.setStyle("-fx-text-fill: #e74c3c; -fx-background-color: transparent;");
            btnSegui.setDisable(true);
        } else {
            btnSegui.setText("❤ Segui");
            btnSegui.setStyle("-fx-text-fill: #e74c3c; -fx-background-color: transparent; -fx-cursor: hand;");
            btnSegui.setDisable(false);
        }
    }
    @FXML
    public void onBackClick(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Search.fxml"));
            Parent homeRoot = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            Scene scene = new Scene(homeRoot, 800, 600);
            stage.setScene(scene);

        } catch (IOException e) {
            ErrorHandler.show(new LoadPageException(e.getMessage()));
        }
    }
    
    @FXML
    public void onAddToCartClick(ActionEvent event) {

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        CollectableCardBean selectedCard = resultsTable.getSelectionModel().getSelectedItem();

        if(SessionManager.getInstance().getLoggedUser()!=null){


            if(selectedCard == null){
                logger.log(Level.WARNING, "Seleziona prima una carta");
            } else {

                ManageCartController cartController = new ManageCartController();
                if(!cartController.addToCart(selectedCard)){
                    ErrorHandler.show(new InvalidInputException("Impossibile aggiungere la carta"));
                }

                ToastManager.showToast(stage, "✅ '" + selectedCard.getName() + "' aggiunta al carrello!");

                resultsTable.getSelectionModel().clearSelection();

                logger.log(Level.INFO, "Carta aggiunta con successo!");
            }

        } else {
            ToastManager.showErrorToast(stage, "Errore, utente non loggato!");
            sceneManager.startScene(event, "/GUI/Login.fxml");
        }



    }

    public void initData(List<CollectableCardBean> risultati) {

        if (risultati == null || risultati.isEmpty()) {
            if (lblMessage != null) {
                lblMessage.setText("Nessuna carta trovata con i filtri selezionati.");
                lblMessage.setVisible(true);
            }
            return;
        } else {
            if (lblMessage != null) lblMessage.setVisible(false);
        }

        ObservableList<CollectableCardBean> datiTabella = FXCollections.observableArrayList(risultati);

        resultsTable.setItems(datiTabella);

    }
}



