package com.example.progettoispw.pattern.decorator;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;

import java.util.List;

public class BaseSearch implements SearchComponent{

    private String nome;

    public BaseSearch(String nome) {
        this.nome = nome;
    }

    @Override
    public List<Card> executeSearch() {

        //CHIAMA IL DAO E FACENDO LA RICERCA PER NOME
        //SOLO QUESTO COMPONENTE UTILIZZA IL DAO PERCHè FA UNA PRIMA RICERCA PORTANDO IN MEMORIA I DATI GREZZI

        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();
        return catalogDAO.findCard(nome);

    }
}
