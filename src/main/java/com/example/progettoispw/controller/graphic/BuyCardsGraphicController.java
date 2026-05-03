package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

        OrderBean datiCheckout = new OrderBean();
        datiCheckout.setNameSurname(txtNome.getText().trim());
        datiCheckout.setShippingAddress(txtIndirizzo.getText().trim() + ", " + txtCap.getText().trim() + " " + txtCitta.getText().trim());
        datiCheckout.setCityName(txtCitta.getText().trim());
        datiCheckout.setPaymentCard(txtNumeroCarta.getText().trim());
        datiCheckout.setCvv(txtCvv.getText().trim());

        try {

            User utenteLoggato = SessionManager.getInstance().getLoggedUser();

            OrderBean riepilogoOrdine = buyController.compileOrder(datiCheckout, utenteLoggato.getUsername());

            OrderOutcomeGraphicController outcomeController = sceneManager.startSceneAndGetController(
                    event,
                    "/GUI/OrderOutcome.fxml"
            );

            outcomeController.initData(riepilogoOrdine);

        } catch (BaseException e) {
            ErrorHandler.show(e);
        }
    }

    @FXML
    public void onBackClick(ActionEvent event) {
        sceneManager.startSceneAndGetController(event,"/GUI/Cart.fxml");
    }
}
