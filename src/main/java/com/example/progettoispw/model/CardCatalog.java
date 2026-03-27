package com.example.progettoispw.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CardCatalog implements Serializable {

    //scelta di progetto ---> ogni catalogo viene identificato tramite il suo seller

    private List<Card> cards;
    private Seller seller;

    private static final long serialVersionUID = 1L;

    public CardCatalog( Seller seller) {
        this.cards = new ArrayList<>();
        this.seller = seller;
    }

    public void addCollectableCard(Card card) {
        this.cards.add(card);
    }

    public Seller getSeller(){
        return seller;
    }

    public List<Card> getCards(){
        return cards;
    }
}
