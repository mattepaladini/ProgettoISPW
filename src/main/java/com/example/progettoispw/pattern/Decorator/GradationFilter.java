package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Gradazione;

import java.util.List;

public class GradationFilter extends SearchDecorator{

    private Gradazione gradation;

    public GradationFilter(SearchComponent searchComponent, Gradazione gradation) {
        super(searchComponent);
        this.gradation = gradation;
    }

    @Override
    public List<Card> executeSearch() {

        List<Card> list = super.executeSearch();

        return list.stream().filter(c -> c.getGradazione().equals(gradation)).toList();
    }
}
