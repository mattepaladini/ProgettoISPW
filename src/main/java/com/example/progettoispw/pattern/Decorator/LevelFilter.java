package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class LevelFilter extends SearchDecorator{

    private int level;

    public LevelFilter(SearchComponent searchComponent, int level) {
        super(searchComponent);
        this.level = level;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {

        List<CollectableCardBean> list = super.executeSearch();
        return list.stream().filter(c -> c.getLivello()==level).toList();
        //INSERISCI LOGICA FILTRAGGIO PER LIVELLO

    }
}
