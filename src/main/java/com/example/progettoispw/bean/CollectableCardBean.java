package com.example.progettoispw.bean;

import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Gradation;
import com.example.progettoispw.model.Type;

public class CollectableCardBean {

    private String name;
    private float price;
    private Gradation gradation;
    private int level;
    private Attribute attribute;
    private Type type;
    private String seller;

    //costruttore
    public CollectableCardBean() {

    }

    public CollectableCardBean(String name, float price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {return name;}
    public void setName(String name) {
        if (name == null) {
            throw new IllegalArgumentException("nome è vuoto");
        }
        this.name = name;
    }


    public float getPrice() {return price;}
    public void setPrice(float prezzo) {
        if(prezzo<=0){
            throw new IllegalArgumentException("prezzo non valido");
        }
        this.price =prezzo;
    }

    public Gradation getGradation() {return gradation;}
    public void setGradation(Gradation gradation) {
        if(gradation ==null){
            throw new IllegalArgumentException("gradation non valido");
        }
        this.gradation = gradation;
        }

    public int getLevel() {return level;}
    public void setLevel(int level) {
        if(level <0){
            throw new IllegalArgumentException("level non valido");
        }
        this.level = level;
    }

    public Attribute getAttribute() {return attribute;}
    public void setAttribute(Attribute attribute) {
        if(attribute ==null){
            throw new IllegalArgumentException("attribute non valido");
        }
        this.attribute = attribute;
    }

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

    public String getSeller() {return seller;}
    public void setSeller(String seller) {this.seller = seller;}

}
