package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;
import java.util.stream.Collectors;

public class MaxPriceFilter extends SearchDecorator{
    private float maxPrice;

    public MaxPriceFilter(SearchComponent searchComponent, float maxPrice) {
        super(searchComponent);
        this.maxPrice = maxPrice;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {
        List<CollectableCardBean> listaGrezza = super.executeSearch();

        //apro uno stream sulla listagrezza del livello precedente -> filtro in base a un predicato e toList organizza i risultati del filtro in una lista
        return listaGrezza.stream().filter(c -> c.getPrezzoCorrente() <= maxPrice).toList();
    }
}
