package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Controller.Logic.SearchController;
import com.example.progettoispw.bean.CollectableCardBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.awt.*;
import java.io.IOException;
import java.util.List;

public class SearchGraphicController {

@FXML private TextField nomeCartaCercata;
@FXML private ComboBox fasciaPrezzoCercata;
@FXML private CheckBox livelloCercato;
@FXML private CheckBox attributoCercato;
@FXML private CheckBox tipoCercato;


    //METODO PER VISUALIZZARE I DATI A SCHERMO ---> CHIAMA SEARCH RESULTS GRAPHIC CONTROLLER
    public void searchAction(ActionEvent event) {
        CollectableCardBean searchCardBean = new CollectableCardBean();

        //inserire i dati dentro la bean
        searchCardBean.setNomeCarta(nomeCartaCercata.getText());

        if(fasciaPrezzoCercata.isPressed()) {
            searchCardBean.setPrezzoCorrente((Float) fasciaPrezzoCercata.getValue());
        }

        if(livelloCercato.isPressed()) {
            searchCardBean.setLivello(Integer.parseInt(livelloCercato.getText()));
        }

        if(attributoCercato.isPressed()) {
            searchCardBean.setAttributo(attributoCercato.getText());
        }
        if(tipoCercato.isPressed()) {
            searchCardBean.setTipo(tipoCercato.getText());
        }

        //CREO IL CONTROLLORE PER LA RICERCA E GLI PASSO LA BEAN APPENA POPOLATA
        SearchController searchController = new SearchController();
        List<CollectableCardBean> risulati = searchController.searchCards(searchCardBean);

        //
        try {
            // 2. CARICAMENTO DEL FILE FXML DEI RISULTATI
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/SearchResults.fxml"));
            Parent resultsView = loader.load();

            // 3. PASSAGGIO DEI DATI AL NUOVO CONTROLLER
            // RIPRENDO IL CONTROLLER CHE HO GIà ISTANZIATO
            SearchResultsGraphicController resultsController = loader.getController();

            // PASSO LA LISTA E IL CONTROLLER
            resultsController.initData(risulati, searchController);

            // 4. SOSTITUZIONE DELLA VISTA CENTRALE
            // Dobbiamo risalire al BorderPane principale (MainLayout) per cambiare il centro
            // (Trucco per trovare la scena padre partendo dal bottone cliccato)
            Node source = (Node) event.getSource();
            Scene scene = source.getScene();
            BorderPane mainLayout = (BorderPane) scene.getRoot();

            // Prendiamo lo StackPane centrale
            StackPane centerPane = (StackPane) mainLayout.getCenter();

            // Sostituiamo il contenuto
            centerPane.getChildren().clear();
            centerPane.getChildren().add(resultsView);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }

    public void showResults(ActionEvent event, List<CollectableCardBean> risultati) throws IOException {


    }



}


