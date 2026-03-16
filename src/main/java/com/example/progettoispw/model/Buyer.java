package com.example.progettoispw.model;

import java.io.Serializable;

public class Buyer extends User implements Serializable {

    private static final long serialVersionUID = 1L;

    public Buyer(String username, String password) {
        super(username, password, UserType.BUYER);
    }
}
