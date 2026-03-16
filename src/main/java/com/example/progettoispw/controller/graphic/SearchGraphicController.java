package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Type;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.util.List;

public class SearchGraphicController {

@FXML private TextField nomeCartaCercata;
@FXML private ComboBox<String> prezzoCercato;
@FXML private ComboBox<Integer> livelloCercato;
@FXML private ComboBox<Attribute> attributoCercato;
@FXML private ComboBox<Type> tipoCercato;

    private static final SceneManager sceneManager = new SceneManager();

    @FXML
    public void onBackClick(ActionEvent event) {

        sceneManager.startScene(event,"/GUI/Home.fxml");

    }

    //METODO PER VISUALIZZARE I DATI A SCHERMO ---> CHIAMA SEARCH RESULTS GRAPHIC CONTROLLER
    public void searchAction(ActionEvent event) {
        CollectableCardBean searchCardBean = new CollectableCardBean();

        //inserire i dati dentro la bean
        searchCardBean.setNomeCarta(nomeCartaCercata.getText());

        //cattura del prezzo desiderato
        Float prezzo = 0.0f;        //valore di default per disattivare il limite del prezzo
        String selezionePrezzo = prezzoCercato.getValue();
        if(selezionePrezzo!=null && !selezionePrezzo.isEmpty()) {

            prezzo = Float.parseFloat(selezionePrezzo.replaceAll("\\D", ""));
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

        SearchResultsGraphicController resultsGraphicController = sceneManager.startSceneAndGetController(event,"/GUI/SearchResults.fxml");
        resultsGraphicController.initData(risultati);

    }

    @FXML
    public void initialize(){

        attributoCercato.setItems(FXCollections.observableArrayList(Attribute.values()));

        tipoCercato.setItems(FXCollections.observableArrayList(Type.values()));
    }

}


