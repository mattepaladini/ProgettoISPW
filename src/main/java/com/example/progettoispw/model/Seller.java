package com.example.progettoispw.model;

import java.io.Serializable;

public class Seller extends User implements Serializable {

    private CardCatalog catalog;

    public Seller(String username) {
        super(username);
    }


    public void addCatalog(CollectableCard card){
        //.......
    }

    public void removeCard(CollectableCard card){
        //.......
    }
    public String getSellerName(){

        return this.getUsername();
    }

}
