package com.example.progettoispw.pattern.decorator;

import com.example.progettoispw.model.Card;

import java.util.List;

public class MaxPriceFilter extends SearchDecorator{
    private float maxPrice;

    public MaxPriceFilter(SearchComponent searchComponent, float maxPrice) {
        super(searchComponent);
        this.maxPrice = maxPrice;
    }

    @Override
    public List<Card> executeSearch() {
        List<Card> listaGrezza = super.executeSearch();

        if(maxPrice==0){return listaGrezza;}

        //apro uno stream sulla lista grezza del livello precedente -> filtro in base a un predicato e toList organizza i risultati del filtro in una lista
        return listaGrezza.stream().filter(c -> c.getPrice() <= maxPrice).toList();
    }
}
