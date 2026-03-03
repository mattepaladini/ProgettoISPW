package com.example.progettoispw.model;

import java.io.Serializable;

public class Seller extends User implements Serializable {

    private CardCatalog catalog;

    private static final long serialVersionUID = 1L;

    public Seller(String username, String password) {
        super(username, password, UserType.SELLER);
    }

    public String getSellerName(){
        return this.getUsername();
    }


    // Stessa cosa del carrello: prendiamo il catalogo per poi inserire all'interno carte.
    public CardCatalog getCardCatalog(){
        return this.catalog;
    }

    public void setCardCatalog(CardCatalog catalog){
        this.catalog = catalog;
    }
}
