package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Type;

import java.util.List;

public class TypeFilter extends SearchDecorator{

    private Type type;

    public TypeFilter(SearchComponent searchComponent, Type type) {
        super(searchComponent);
        this.type = type;
    }

    @Override
    public List<CollectableCardBean> executeSearch() {

        List<CollectableCardBean> list = super.executeSearch();
        return list.stream().filter(c-> c.getTipo().equals(type)).toList();
    }
}
