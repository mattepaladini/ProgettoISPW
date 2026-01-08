package com.example.progettoispw.model;

import java.util.List;

public class Order {

    private int id;
    private List<Card> carteOrdinate;
    private String indirizzoSpedizione;
    private User compratore;

    public Order(List<Card> carteOrdinate, String indirizzoSpedizione, User compratore) {
        this.carteOrdinate = carteOrdinate;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.compratore = compratore;
    }

    public List<Card> getCarteOrdinate() {
        return carteOrdinate;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public User getCompratore() {
        return compratore;
    }

    public boolean checkAviability(){

        // cerca se le carte che sono presenti nell'ordine sono disponibili prima di effettuare l'ordine
        return false;
    }
}
