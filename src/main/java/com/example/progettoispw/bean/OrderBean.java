package com.example.progettoispw.bean;

import java.util.List;

public class OrderBean {

    private int orderId;
    private List<CollectableCardBean> cards;

    public OrderBean() {

    }

    public int getOrderId(){
        return orderId;
    }

    public void setOrderId(int orderId){
        this.orderId = orderId;
    }

    public List<CollectableCardBean> getCards(){
        return cards;
    }

    public void setCards(List<CollectableCardBean> cards){
        this.cards = cards;
    }
}
