package com.example.progettoispw.pattern.Decorator;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;

import java.util.List;

public interface SearchComponent {

    List<Card> executeSearch();
}
