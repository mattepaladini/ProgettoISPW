package com.example.progettoispw.model;

import java.util.List;

public class Order {

    private int id;
    private List<Card> carteOrdinate;
    private String indirizzoSpedizione;
    private String compratore;
    private float totale;
    private String dataOrdine;

    public Order(int id, List<Card> carteOrdinate, String indirizzoSpedizione, String compratore, float totale, String dataOrdine) {
        this.id = id;
        this.carteOrdinate = carteOrdinate;
        this.indirizzoSpedizione = indirizzoSpedizione;
        this.compratore = compratore;
        this.totale = totale;
        this.dataOrdine = dataOrdine;
    }

    public List<Card> getCarteOrdinate() {
        return carteOrdinate;
    }

    public String getIndirizzoSpedizione() {
        return indirizzoSpedizione;
    }

    public String getDataOrdine(){return dataOrdine;}

    public String getCompratore() {
        return compratore;
    }

    public float getTotale() {return totale;}

    public int getId() {
        return id;
    }
}
