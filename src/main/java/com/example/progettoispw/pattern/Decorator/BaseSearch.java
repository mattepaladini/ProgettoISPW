package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class BaseSearch implements SearchComponent{

    private String nome;

    public BaseSearch(String nome) {
        this.nome = nome;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {

        //CHIAMA IL DAO E FACENDO LA RICERCA PER NOME
        //SOLO QUESTO COMPONENTE UTILIZZA IL DAO PERCHè FA UNA PRIMA RICERCA PORTANDO IN MEMORIA I DATI GREZZI
        return List.of();
    }
}
