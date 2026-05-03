package com.example.progettoispw.model;

import java.io.Serializable;

public class Card implements  Serializable {

    //scelta di progetto ---> ogni carta viene identificata in un catalogo con il suo nome in quanto si assume che
    //                        un venditore non possa avere in vendita altre carte con quel nome

    private String name;
    private Float price;
    private Gradation gradation;
    private String seller;
    private int level;
    private Attribute attribute;
    private Type type;

    public Card(String name, Float price, Gradation gradation, String seller, int level, Attribute attribute, Type type) {
        this.name = name;
        this.price = price;
        this.gradation = gradation;
        this.seller = seller;
        this.level = level;
        this.attribute = attribute;
        this.type = type;
    }


    public Card(String name, Float prezzo){
        this.name = name;
        this.price = prezzo;
    }

    //costruttore usato per memorizzare nel file "orders.txt" le informazioni delle carte ordinate
    public Card(String name, String seller){
        this.name = name;
        this.seller = seller;
    }


    public String getName() {
        return name;
    }

    public Float getPrice(){
        return this.price;
    }

    public void setPrice(Float price){
        this.price = price;
    }

    public Gradation getGradation(){
        return this.gradation;
    }

    public String getSeller(){
        return this.seller;
    }


    public int getLevel(){return this.level;}

    public Attribute getAttribute(){return this.attribute;}

    public Type getType(){return this.type;}

}
