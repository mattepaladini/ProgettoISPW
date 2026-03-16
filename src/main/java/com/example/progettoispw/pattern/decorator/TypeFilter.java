package com.example.progettoispw.pattern.decorator;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Type;

import java.util.List;

public class TypeFilter extends SearchDecorator{

    private Type type;

    public TypeFilter(SearchComponent searchComponent, Type type) {
        super(searchComponent);
        this.type = type;
    }

    @Override
    public List<Card> executeSearch() {

        List<Card> list = super.executeSearch();
        return list.stream().filter(c-> c.getTipo().equals(type)).toList();
    }
}
