package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class GradationFilter extends SearchDecorator{

    private String gradation;

    public GradationFilter(SearchComponent searchComponent, String gradation) {
        super(searchComponent);
        this.gradation = gradation;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {

        List<CollectableCardBean> list = super.executeSearch();

        return list.stream().filter(c -> c.getGradazione().equalsIgnoreCase(gradation)).toList();
    }
}
