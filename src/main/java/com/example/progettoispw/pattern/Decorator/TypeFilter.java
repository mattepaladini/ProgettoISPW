package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Type;

import java.util.List;
import java.util.stream.Collectors;

public class TypeFilter extends SearchDecorator{

    private Type type;

    public TypeFilter(SearchComponent searchComponent, Type type) {
        super(searchComponent);
        this.type = type;
    }

    @Override
    public List<Card> executeSearch() {

        List<Card> list = super.executeSearch();
        return list.stream().filter(c-> c.getTipo().equals(type)).collect(Collectors.toList());
    }
}
