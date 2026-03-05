package com.example.progettoispw.model;

import java.io.Serializable;
import java.util.List;

public class Buyer extends User implements Serializable {

    //private ShoppingCart cart;
    //private List<Order> orders;

    private static final long serialVersionUID = 1L;

    public Buyer(String username, String password) {
        super(username, password, UserType.BUYER);
        //this.cart = new ShoppingCart();
    }

/*
    // Utilizziamo questo metodo per prendere il carrello e successivamente inserire carte all'interno.
    public ShoppingCart getCart(){
        return this.cart;
    }

    // metodo inserito per l'eventuale eliminazione del carrello precedente.
    public void setCart(ShoppingCart cart){
        this.cart = cart;
    }


    public List<Order> getOrders(){
        return this.orders;
    }

    public void addOrder(Order order){
        this.orders.add(order);
    }

     */
}
