package com.example.progettoispw.pattern.decorator;

import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Card;

import java.util.List;

public class AttributeFilter extends SearchDecorator{

    private Attribute attribute;

    public AttributeFilter(SearchComponent searchComponent, Attribute attribute) {
        super(searchComponent);
        this.attribute = attribute;
    }

    @Override
    public List<Card> executeSearch() {
        List<Card> list = super.executeSearch();

        return list.stream().filter(c-> c.getAttributo().equals(attribute)).toList();
    }
}
