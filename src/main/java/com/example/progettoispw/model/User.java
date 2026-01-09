package com.example.progettoispw.model;

import java.util.List;

public class User {

    //private List<Card> carrello;
    private String username;
    private String password;
    private String tipoUtente;

    protected User(){}

    public User(String username) {
        this.username = username;
    }


    public String getUsername() {
        return username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }
}
