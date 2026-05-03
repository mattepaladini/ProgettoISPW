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
        if (name != null) {
            this.name = name;
        }
    }


    public float getPrice() {return price;}
    public void setPrice(float prezzo) {
        if(prezzo>0){
            this.price =prezzo;
        }
    }

    public Gradation getGradation() {return gradation;}
    public void setGradation(Gradation gradation) {
        if(gradation !=null){
            this.gradation = gradation;
        }
        }

    public int getLevel() {return level;}
    public void setLevel(int level) {
        if(level >0){
            this.level = level;
        }
    }

    public Attribute getAttribute() {return attribute;}
    public void setAttribute(Attribute attribute) {
        if(attribute !=null){
            this.attribute = attribute;
        }
    }

    public Type getType() {return type;}
    public void setType(Type type) {this.type = type;}

    public String getSeller() {return seller;}
    public void setSeller(String seller) {this.seller = seller;}

}
