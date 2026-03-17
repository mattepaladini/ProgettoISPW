package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCartController;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.LoadPageException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    public void initialize() {

        btnAddToCart.disableProperty().bind(
                resultsTable.getSelectionModel().selectedItemProperty().isNull());
//disabilito il bottone per aggiungere al carrello finchè non viene selezionata la riga della tabella

        //la stringa finale dipende da getter che si trova in CollecatableCardBean
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCarta"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoCorrente"));
        colGradazione.setCellValueFactory(new PropertyValueFactory<>("gradazione"));
        colVenditore.setCellValueFactory(new PropertyValueFactory<>("venditore"));
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
        CollectableCardBean selectedCard = resultsTable.getSelectionModel().getSelectedItem();

        if(selectedCard == null){
            logger.log(Level.WARNING, "Seleziona prima una carta");
        } else {

            ManageCartController cartController = new ManageCartController();
            if(!cartController.addToCart(selectedCard)){
                ErrorHandler.show(new InvalidInputException("Impossibile aggiungere la carta"));
            }

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

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
