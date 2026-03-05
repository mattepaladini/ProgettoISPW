package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.bean.CollectableCardBean;

import com.example.progettoispw.exception.loadPageException;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Type;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

public class SearchGraphicController {

@FXML private TextField nomeCartaCercata;
@FXML private ComboBox prezzoCercato;
@FXML private ComboBox livelloCercato;
@FXML private ComboBox<Attribute> attributoCercato;
@FXML private ComboBox<Type> tipoCercato;

    private static final Logger logger = Logger.getLogger(SearchGraphicController.class.getName());


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

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Home.fxml");
        }
    }

    //METODO PER VISUALIZZARE I DATI A SCHERMO ---> CHIAMA SEARCH RESULTS GRAPHIC CONTROLLER
    public void searchAction(ActionEvent event) {
        CollectableCardBean searchCardBean = new CollectableCardBean();

        //inserire i dati dentro la bean
        searchCardBean.setNomeCarta(nomeCartaCercata.getText());

        //cattura del prezzo desiderato
        Float prezzo = 0.0f;        //valore di default per disattivare il limite del prezzo
        String selezionePrezzo = (String) prezzoCercato.getValue();
        if(selezionePrezzo!=null && !selezionePrezzo.isEmpty()) {

            prezzo = Float.parseFloat(selezionePrezzo.replaceAll("[^0-9]", ""));
            System.out.println(prezzo);

        }
        searchCardBean.setPrezzoCorrente(prezzo);

        //-----------------------//
        if(livelloCercato.getValue()!=null) {
            searchCardBean.setLivello(Integer.parseInt(livelloCercato.getValue().toString()));
        }

        if(attributoCercato.getValue()!=null) {
            searchCardBean.setAttributo(attributoCercato.getValue());
        }
        if(tipoCercato.getValue()!=null) {
            searchCardBean.setTipo(tipoCercato.getValue());
        }

        //CREO IL CONTROLLORE PER LA RICERCA E GLI PASSO LA BEAN APPENA POPOLATA
        BuyController buyController = new BuyController();
        List<CollectableCardBean> risultati = buyController.searchCards(searchCardBean);

        //
        try {
            // 1. CARICAMENTO DEL FILE FXML DEI RISULTATI
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/SearchResults.fxml"));
            Parent resultsView = loader.load();

            // 2. PASSAGGIO DEI DATI AL NUOVO CONTROLLER
            SearchResultsGraphicController resultsController = loader.getController();

            // Passo la lista dei risultati e il controller precedente (per poter tornare indietro)
            resultsController.initData(risultati, buyController);

            // 3. SOSTITUZIONE DELL'INTERA SCENA (Cambio pagina)
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(resultsView, 800, 600));
            stage.show(); // Assicuriamoci che la finestra si aggiorni

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Search.fxml");
        }

    }

    @FXML
    public void initialize(){

        attributoCercato.setItems(FXCollections.observableArrayList(Attribute.values()));

        tipoCercato.setItems(FXCollections.observableArrayList(Type.values()));
    }

}


