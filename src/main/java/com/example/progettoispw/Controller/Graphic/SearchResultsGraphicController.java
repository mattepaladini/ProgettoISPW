package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Controller.Logic.BuyController;
import com.example.progettoispw.Controller.Logic.RegistrationController;
import com.example.progettoispw.bean.CollectableCardBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;

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

    private BuyController buyCardController;


    public void initData(List<CollectableCardBean> risultati, BuyController buyCardController) {

        this.buyCardController = buyCardController;

        if (risultati == null || risultati.isEmpty()) {
            if (lblMessage != null) {
                lblMessage.setText("Nessuna carta trovata con i filtri selezionati.");
                lblMessage.setVisible(true);
            }
            return;
        } else {
            if (lblMessage != null) lblMessage.setVisible(false);
        }

        try {
            for (CollectableCardBean card : risultati) {
                // Carichiamo il file FXML della singola riga ("CardItem")
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/CardItem.fxml"));
                Parent cardNode = loader.load();

                // Recuperiamo il controller grafico della singola riga
                CardItemController itemController = loader.getController();

                // PASSAGGIO FONDAMENTALE:
                // Passiamo alla riga i dati della carta E il controller logico.
                // Così se l'utente clicca "Acquista" su QUESTA riga, itemController saprà chi chiamare.
                itemController.setCardData(card, this.buyCardController);

                // Aggiungiamo la riga grafica al contenitore verticale
                resultsContainer.getChildren().add(cardNode);
            }
        } catch (IOException e) {
            logger.log(Level.INFO, "errore nel caricamento dei risultati", e.getMessage());
        }


    }
}
