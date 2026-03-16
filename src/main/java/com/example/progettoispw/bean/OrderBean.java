package com.example.progettoispw.bean;

import java.util.List;

public class OrderBean {

    private int orderId;
    private List<CollectableCardBean> cards;

    private float totale;
    private String purchaseDate;

    private String shippingAddress;
    private String nameSurname;
    private String cityName;
    private String paymentCard;
    private String cvv;


    public OrderBean() {
        //COSTRUTTORE OrderBean
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

    public float getTotale(){ return totale; }
    public void setTotale(float totale){ this.totale = totale; }

    public String getPurchaseDate() { return purchaseDate; }
    public void setPurchaseDate(String purchaseDate) { this.purchaseDate = purchaseDate; }

    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }

    public String getNameSurname() { return nameSurname; }
    public void setNameSurname(String nameSurname) { this.nameSurname = nameSurname; }

    public String getCityName() { return cityName; }
    public void setCityName(String cityName) { this.cityName = cityName; }

    public String getPaymentCard() { return paymentCard; }
    public void setPaymentCard(String paymentCard) { this.paymentCard = paymentCard; }

    public String getCvv() { return cvv; }
    public void setCvv(String cvv) { this.cvv = cvv; }
}
