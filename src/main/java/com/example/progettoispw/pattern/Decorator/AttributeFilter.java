package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Attribute;

import java.util.List;

public class AttributeFilter extends SearchDecorator{

    private Attribute attribute;

    public AttributeFilter(SearchComponent searchComponent, Attribute attribute) {
        super(searchComponent);
        this.attribute = attribute;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {
        List<CollectableCardBean> list = super.executeSearch();

        return list.stream().filter(c-> c.getAttributo().equals(attribute)).toList();
    }
}
