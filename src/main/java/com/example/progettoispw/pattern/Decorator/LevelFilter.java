package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;

import java.util.List;

public class LevelFilter extends SearchDecorator{

    private int level;

    public LevelFilter(SearchComponent searchComponent, int level) {
        super(searchComponent);
        this.level = level;
    }

    @Override
    public List<Card> executeSearch() {

        List<Card> list = super.executeSearch();
        return list.stream().filter(c -> c.getLivello()==level).toList();
        //INSERISCI LOGICA FILTRAGGIO PER LIVELLO

    }
}
