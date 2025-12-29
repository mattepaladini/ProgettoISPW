package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class SearchDecorator implements SearchComponent{

    protected SearchComponent searchComponent;      //riferimento per la ricorsione del pattern

    public SearchDecorator(SearchComponent searchComponent) {
        this.searchComponent = searchComponent;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {
        return searchComponent.executeSearch();
    }
}
