package com.example.progettoispw.bean;

import com.example.progettoispw.bean.CollectableCardBean;

public class CartRowBean {

    private CollectableCardBean card;
    private float prezzoTotale;

    public CartRowBean(){}

    public CartRowBean(CollectableCardBean card, float prezzoTotale) {}

    public CollectableCardBean getCard() {return card;}
    public void setCard(CollectableCardBean card) {this.card = card;}

    public float getPrezzoTotale() {return prezzoTotale;}
    public void setPrezzoTotale() {this.prezzoTotale = prezzoTotale;}

}
