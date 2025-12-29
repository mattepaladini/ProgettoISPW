package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;

import java.util.List;

public class AttributeFilter extends SearchDecorator{

    private String attribute;

    public AttributeFilter(SearchComponent searchComponent, String attribute) {
        super(searchComponent);
        this.attribute = attribute;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {
        List<CollectableCardBean> list = super.executeSearch();

        return list.stream().filter(c-> c.getAttributo().equalsIgnoreCase(attribute)).toList();
    }
}
