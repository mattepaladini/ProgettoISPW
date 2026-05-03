package com.example.progettoispw.pattern.decorator;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Gradation;

import java.util.List;

public class GradationFilter extends SearchDecorator{

    private Gradation gradation;

    public GradationFilter(SearchComponent searchComponent, Gradation gradation) {
        super(searchComponent);
        this.gradation = gradation;
    }

    @Override
    public List<Card> executeSearch() {

        List<Card> list = super.executeSearch();

        return list.stream().filter(c -> c.getGradation().equals(gradation)).toList();
    }
}
