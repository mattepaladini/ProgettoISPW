package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class TypeFilter extends SearchDecorator{

    private String type;

    public TypeFilter(SearchComponent searchComponent, String type) {
        super(searchComponent);
        this.type = type;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {

        List<CollectableCardBean> list = super.executeSearch();
        return list.stream().filter(c-> c.getTipo().equalsIgnoreCase(type)).toList();
    }
}
