package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.exception.operationfailedException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class BuyCardsGraphicController {

    @FXML private TextField txtNome;
    @FXML private TextField txtIndirizzo;
    @FXML private TextField txtCitta;
    @FXML private TextField txtCap;
    @FXML private TextField txtNumeroCarta;
    @FXML private TextField txtScadenza;
    @FXML private TextField txtCvv;

    @FXML private Label lblData;
    @FXML private Label lblIndirizzo;
    @FXML private Label lblOrderId;
    @FXML private Label lblNumeroArticoli;
    @FXML private Label lblTotale;

    private BuyController buyController;
    private  SceneManager sceneManager;

    @FXML
    public void initialize() {
        this.buyController = new BuyController();
        this.sceneManager = new SceneManager();
    }

    // Il metodo che userai dal Carrello per iniettare i dati prima di mostrare la scena!
    public void initData(int numeroArticoli, float totalePagare) {
        lblNumeroArticoli.setText(String.valueOf(numeroArticoli));
        lblTotale.setText(String.format("%.2f €", totalePagare));
    }

    @FXML
    public void onConfirmClick(ActionEvent event) {
        // 1. VALIDAZIONE FRONT-END (Campi obbligatori)
       /* if (txtNome.getText().trim().isEmpty() ||
                txtIndirizzo.getText().trim().isEmpty() ||
                txtCitta.getText().trim().isEmpty() ||
                txtNumeroCarta.getText().trim().isEmpty()) {

            mostraErrore("Dati Incompleti", "Per favore, compila tutti i campi obbligatori per la spedizione e il pagamento.");
            return; // Blocchiamo l'esecuzione
        }*/

        // 2. CREAZIONE DEL BEAN DI INPUT
        OrderBean datiCheckout = new OrderBean();
        datiCheckout.setNameSurname(txtNome.getText().trim());
        datiCheckout.setShippingAddress(txtIndirizzo.getText().trim() + ", " + txtCap.getText().trim() + " " + txtCitta.getText().trim());
        datiCheckout.setPaymentCard(txtNumeroCarta.getText().trim());
        datiCheckout.setCvv(txtCvv.getText().trim());

        try {
            // 3. RECUPERO UTENTE LOGGATO
            User utenteLoggato = SessionManager.getInstance().getLoggedUser();

            // 4. CHIAMATA AL CONTROLLER APPLICATIVO (La logica di business)
            OrderBean riepilogoOrdine = buyController.compileOrder(datiCheckout, utenteLoggato);

            // 5. CAMBIO SCENA ALLA PAGINA DI SUCCESSO
            OrderOutcomeGraphicController outcomeController = sceneManager.startSceneAndGetController(
                    event,
                    "/GUI/OrderOutcome.fxml" // Modifica il path se necessario
            );

            // 6. INIETTIAMO I DATI DEL RIEPILOGO NELLA NUOVA SCHERMATA
            outcomeController.initData(riepilogoOrdine);

        } catch (Exception e) {
            throw new operationfailedException(e.getMessage());
        }
    }

    /**
     * Metodo di supporto per mostrare i popup di errore.
     */
    private void mostraErrore(String titolo, String messaggio) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titolo);
        alert.setHeaderText(null);
        alert.setContentText(messaggio);
        alert.showAndWait();
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneManager.startSceneAndGetController(event,"/GUI/Cart.fxml");
    }
}
