package com.example.progettoispw.pattern.Observer;

import com.example.progettoispw.model.Card;

public interface PriceObserver {

    void updatePrice(Card modifiedCard);
}
