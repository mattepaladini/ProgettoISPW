package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.exception.invalidInputException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.pattern.Decorator.*;

import java.util.ArrayList;
import java.util.List;

public class BuyController {




    public List<CollectableCardBean> searchCards(CollectableCardBean searchBean){

        //DECORATOR *****************++

        String nome = "";
        if(searchBean.getNomeCarta().isBlank())
        {
            throw new invalidInputException("Errore, inserire il nome della carta da cercare");
        } else{
            nome = searchBean.getNomeCarta();
        }

        SearchComponent searchStack = new BaseSearch(nome);

        if(searchBean.getPrezzoCorrente()>0.0f){
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

        //DECORATOR *****************++

        List<Card> carteTrovate = searchStack.executeSearch();

        //MAPPING
        List<CollectableCardBean> risultatiBean = new ArrayList<>();

        for (Card carta : carteTrovate) {
            // Creiamo un nuovo Bean vuoto per ogni carta trovata
            CollectableCardBean bean = new CollectableCardBean();

            // "Travasiamo" i dati dall'Entità al Bean
            bean.setNomeCarta(carta.getNome());
            bean.setPrezzoCorrente(carta.getPrezzoAttuale());
            bean.setLivello(carta.getLivello());
            bean.setGradazione(carta.getGradazione());
            bean.setTipo(carta.getTipo());
            bean.setAttributo(carta.getAttributo());

            bean.setVenditore(carta.getVenditore());

            // Aggiungiamo il Bean pronto alla lista finale
            risultatiBean.add(bean);
        }

        // 5. RITORNO AL CONTROLLER GRAFICO
        return risultatiBean;

    }
}
