package com.example.progettoispw.model;

public class BaseUser extends User {

    private ShoppingCart cart;

    public BaseUser(String username, String password) {
        super(username, password, UserType.BASE);
        this.cart = new ShoppingCart();
    }

    public ShoppingCart getShoppingCart(){
        return this.cart;
    }

    public void setCart(){
        this.cart = new ShoppingCart();
    }
}
