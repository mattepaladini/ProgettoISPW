package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.OrderBean;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class OrderOutcomeGraphicController {

    @FXML private Label lblOrderId;
    @FXML private Label lblTotale;
    @FXML private Label lblData;
    @FXML private Label lblIndirizzo;

    private SceneManager sceneManager;

    public void initData(OrderBean riepilogoOrdine) {

        // Formattiamo l'ID per farlo sembrare un vero numero d'ordine (es. #00014)
        lblOrderId.setText(String.format("#%05d", riepilogoOrdine.getOrderId()));

        // Mostriamo il totale
        lblTotale.setText(String.format("%.2f €", riepilogoOrdine.getTotale()));

        // Mostriamo la data
        lblData.setText(riepilogoOrdine.getPurchaseDate());

        // Mostriamo l'indirizzo
        lblIndirizzo.setText(riepilogoOrdine.getShippingAddress());

        this.sceneManager = new SceneManager();
    }

    @FXML
    public void onBackToHomeClick(ActionEvent event) {
        sceneManager.startScene(event, "/GUI/Home.fxml");
    }
}
