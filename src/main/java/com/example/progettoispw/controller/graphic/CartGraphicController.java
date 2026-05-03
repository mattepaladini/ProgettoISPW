package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCartController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class CartGraphicController{
    @FXML private TableView<CollectableCardBean> cartTable;
    @FXML private Label lblTotale;
    @FXML private Button btnRimuovi;
    @FXML private Button btnCheckout;

    @FXML
    private TableColumn<CollectableCardBean, String> colNome;

    @FXML
    private TableColumn<CollectableCardBean, Float> colPrezzo;

    @FXML
    private TableColumn<CollectableCardBean, String> colVenditore;

    private ManageCartController appController;

    private SceneManager sceneManager;

    private static final Logger logger = Logger.getLogger(CartGraphicController.class.getName());

    // Metodo chiamato quando si carica la schermata
    // Ipotizziamo che riceva la lista di carte nel carrello
    @FXML
    public void initialize() {
        colNome.setCellValueFactory(new PropertyValueFactory<>("nomeCarta"));
        colVenditore.setCellValueFactory(new PropertyValueFactory<>("venditore"));
        colPrezzo.setCellValueFactory(new PropertyValueFactory<>("prezzoCorrente"));

        //disabilitiamo il bottone "Rimuovi" se non c'è nulla di selezionato
        btnRimuovi.disableProperty().bind(cartTable.getSelectionModel().selectedItemProperty().isNull());

        this.appController = new ManageCartController();

        this.sceneManager = new SceneManager();
        // 3. Carichiamo i dati iniziali
        aggiornaVistaCarrello();
    }

    @FXML
    public void onDeleteClick(ActionEvent event) {
        CollectableCardBean selezionata = cartTable.getSelectionModel().getSelectedItem();

        if (selezionata != null) {
            // Chiamiamo la logica per rimuovere
            boolean successo = appController.removeFromCart(selezionata);

            if (successo) {
                // Ricarica la tabella e il totale!
                aggiornaVistaCarrello();
            } else {
                logger.log(Level.SEVERE, "Impossibile rimuovere la carta dal carrello");

            }
        }
    }

    private void aggiornaVistaCarrello() {

        ObservableList<CollectableCardBean> carteObservable;

        // Chiediamo le carte al Controller Logico
        List<CollectableCardBean> carteNelCarrello = appController.getCardsFromCart();

        // Aggiorniamo la tabella
        carteObservable = FXCollections.observableArrayList(carteNelCarrello);
        cartTable.setItems(carteObservable);

        // Aggiorniamo il totale testuale
        float totale = appController.calculateCartTotal();
        lblTotale.setText(String.format("%.2f €", totale));

        // Se il carrello è vuoto, disabilitiamo il bottone Checkout
        btnCheckout.setDisable(carteNelCarrello.isEmpty());
    }

    @FXML
    public void onCheckoutClick(ActionEvent event) {

        List<CollectableCardBean> cart = appController.getCardsFromCart();

        float totale = 0;
        for(CollectableCardBean cardBean : cart) {
            totale+= cardBean.getPrice();
        }

        BuyCardsGraphicController checkoutController =  sceneManager.startSceneAndGetController(event, "/GUI/BuyCard.fxml");
        checkoutController.initData(cart.size(), totale);
    }
    @FXML
    public void onBackClick(ActionEvent event) {
        try {
            // 1. Carica la Home
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/Home.fxml"));
            Parent homeRoot = loader.load();

            // 2. Recupera lo Stage (la finestra) dal bottone cliccato
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

            // 3. Sostituisci la scena interamente
            Scene scene = new Scene(homeRoot, 800, 600);
            stage.setScene(scene);
            // stage.show(); // Non serve richiamarlo, la finestra è già aperta

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }
}
