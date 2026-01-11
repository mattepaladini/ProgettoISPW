package com.example.progettoispw.model;

public class Customer extends User {
    public Customer(String username, String password) {
        super(username, password, UserType.CUSTOMER);
    }
}
