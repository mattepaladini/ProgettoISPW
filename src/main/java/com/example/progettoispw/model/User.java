package com.example.progettoispw.model;


import java.io.Serializable;
import java.util.List;

public class User implements Serializable {

    //private List<Card> carrello;
    private String username;
    private String password;
    private UserType tipoUtente;
    //private List<Order> ordini;

    private static final long serialVersionUID = 1L;

    protected User() {}

    public User(String username, String password, UserType tipoUtente) {

        this.username = username;
        this.password = password;
        this.tipoUtente = tipoUtente;
    }

    public User(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {return password;}
    public UserType getTipoUtente() {return this.tipoUtente;}
}
