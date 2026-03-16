package com.example.progettoispw.pattern.observer;

import com.example.progettoispw.model.Card;

public interface PriceObserver {

    void updatePrice(Card modifiedCard);
}
