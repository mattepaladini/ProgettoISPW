package com.example.progettoispw.model;

public class BaseUser extends User {

    private ShoppingCart cart;

    public BaseUser(){
        super();
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart(){
        return this.cart;
    }

    public void setCart(){
        this.cart = new ShoppingCart();
    }
}
