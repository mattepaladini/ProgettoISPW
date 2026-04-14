package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCartController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.LoadPageException;
import com.example.progettoispw.exception.OperationFailedException;
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

    private ManageNotificationsController notificationsController= new ManageNotificationsController();;


    @FXML
    public void initialize() {

        btnAddToCart.disableProperty().bind(
                resultsTable.getSelectionModel().selectedItemProperty().isNull());
//disabilito il bottone per aggiungere al carrello finchè non viene selezionata la riga della tabella

        //la stringa finale dipende da getter che si trova in CollecatableCardBean
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCarta"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoCorrente"));
        colGradazione.setCellValueFactory(new PropertyValueFactory<>("gradazione"));
        colVenditore.setCellValueFactory(new PropertyValueFactory<>("venditore"));
        colSegui.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));

        colSegui.setCellFactory(param -> new TableCell<CollectableCardBean, CollectableCardBean>() {
            private final Button btnSegui = new Button("❤ Segui");

            {
                // Stile del bottone inline
                btnSegui.setStyle("-fx-background-color: transparent; -fx-text-fill: #e74c3c; -fx-font-weight: bold; -fx-cursor: hand;");

                btnSegui.setOnAction(event -> {

                    CollectableCardBean cardSelezionata = getTableView().getItems().get(getIndex());
                    String venditoreUsername = cardSelezionata.getVenditore();

                    //se l'utente non è loggato non può mettere il follow
                    if (SessionManager.getInstance().getLoggedUser() == null) {
                        Stage stage = (Stage) btnSegui.getScene().getWindow();
                        ToastManager.showErrorToast(stage, "Devi fare il login per seguire un venditore!");
                        return;
                    }

                    try {


                       if(notificationsController.followSeller(SessionManager.getInstance().getLoggedUser().getUsername(), cardSelezionata.getVenditore())) {

                           btnSegui.setText("✔️ Seguito");
                           btnSegui.setDisable(true);

                           Stage stage = (Stage) btnSegui.getScene().getWindow();
                           ToastManager.showToast(stage, "Ora segui " + venditoreUsername + "!");
                       }



                    } catch (Exception e) {
                        logger.log(Level.SEVERE, "Errore durante il follow", e);
                        Stage stage = (Stage) btnSegui.getScene().getWindow();
                        ToastManager.showErrorToast(stage, "Errore di connessione.");
                    }
                });
            }
            @Override
            protected void updateItem(CollectableCardBean item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                }else{

                    if(SessionManager.getInstance().getLoggedUser().getUsername().equals(item.getVenditore())){
                        btnSegui.setDisable(false);
                        btnSegui.setManaged(false);
                    } else{
                        btnSegui.setDisable(true);
                        boolean isAlreadyFollow = notificationsController.checkFollowStatus(SessionManager.getInstance().getLoggedUser().getUsername(), item.getVenditore());

                        if (isAlreadyFollow) {

                            btnSegui.setText("✔️ Segui già");
                            btnSegui.setStyle("-fx-text-fill: #95a5a6; -fx-background-color: transparent;");
                            btnSegui.setDisable(true);

                        } else {

                            btnSegui.setText("❤ Segui");
                            btnSegui.setStyle("-fx-text-fill: #e74c3c; -fx-background-color: transparent; -fx-cursor: hand;");
                            btnSegui.setDisable(false);
                        }
                    }


                    setGraphic(btnSegui);
                }
            }

        });

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

        if(SessionManager.getInstance().getLoggedUser()==null){
            ToastManager.showErrorToast(stage, "Errore, utente non loggato!");
            throw new OperationFailedException("Errore, utente non loggato!");
        }

        if(selectedCard == null){
            logger.log(Level.WARNING, "Seleziona prima una carta");
        } else {

            ManageCartController cartController = new ManageCartController();
            if(!cartController.addToCart(selectedCard)){
                ErrorHandler.show(new InvalidInputException("Impossibile aggiungere la carta"));
            }





            // 3. LA MAGIA: Chiami il tuo ToastManager!
            ToastManager.showToast(stage, "✅ '" + selectedCard.getNomeCarta() + "' aggiunta al carrello!");

            resultsTable.getSelectionModel().clearSelection();

            logger.log(Level.INFO, "Carta aggiunta con successo!");
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



