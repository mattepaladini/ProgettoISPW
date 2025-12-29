package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Controller.Logic.SearchController;
import com.example.progettoispw.bean.CollectableCardBean;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

import java.awt.*;
import java.util.List;

public class SearchGraphicController {

@FXML private TextField nomeCartaCercata;
@FXML private CheckBox fasciaPrezzoCercata;
@FXML private CheckBox livelloCercato;
@FXML private CheckBox attributoCercato;
@FXML private CheckBox tipoCercato;

    public void searchAction(ActionEvent actionEvent) {
        CollectableCardBean searchCardBean = new CollectableCardBean();

        SearchController searchController = new SearchController();
        List<CollectableCardBean> risulati = searchController.searchCards(searchCardBean);
    }


    //METODO PER VISUALIZZARE I DATI A SCHERMO ---> CHIAMA SEARCH RESULTS GRAPHIC CONTROLLER
}


