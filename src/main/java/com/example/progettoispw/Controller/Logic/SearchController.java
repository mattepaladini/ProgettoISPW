package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.pattern.Decorator.*;

import java.util.List;

public class SearchController {




    public List<CollectableCardBean> searchCards(CollectableCardBean searchBean){

        String nome = "";
        if(searchBean.getNomeCarta().isBlank())
        {
            //DECIDIAMO COSA FARE SE NON VIENE INSERITO IL NOME DELLA CARTA
        } else{
            nome = searchBean.getNomeCarta();
        }

        SearchComponent searchStack = new BaseSearch(nome);

        if(searchBean.getPrezzoCorrente() != 0 && searchBean.getPrezzoCorrente()>0){
            searchStack = new MaxPriceFilter(searchStack, searchBean.getPrezzoCorrente());
        }

        if(searchBean.getAttributo()!=null){
            searchStack = new AttributeFilter(searchStack, searchBean.getAttributo());
        }

        if(searchBean.getTipo()!=null){
            searchStack = new TypeFilter(searchStack, searchBean.getTipo());
        }

        if(searchBean.getLivello()!=0 && searchBean.getLivello()>0){
            searchStack = new MaxPriceFilter(searchStack, searchBean.getLivello());
        }

        if(searchBean.getGradazione()!=null){
            searchStack = new GradationFilter(searchStack, searchBean.getGradazione());
        }

        return searchStack.executeSearch();
    }
}
