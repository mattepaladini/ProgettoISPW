package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.bean.CartRowBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;


public class SearchResultsGraphicController {

    @FXML
    private VBox resultsContainer; // Il contenitore dentro lo ScrollPane

    // Metodo chiamato per popolare la pagina
    public void initializeData(List<CartRowBean> listaCarte) {

        // Pulisce eventuali risultati precedenti
        resultsContainer.getChildren().clear();

        try {
            for (CartRowBean card : listaCarte) {
                // 1. Carica il file FXML della singola riga
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/items/CardItem.fxml"));
                HBox cardItem = loader.load();

                // 2. Prendi il controller di QUELLA riga specifica
                CardItemController itemController = loader.getController();

                // 3. Passagli i dati
                itemController.setData(card);

                // 4. Aggiungi la riga grafica al contenitore verticale
                resultsContainer.getChildren().add(cardItem);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
