package com.example.progettoispw.model;


import java.io.Serializable;

public class User implements Serializable {

    //scelta di progetto ---> ogni user viene identificato dalla coppia (username, password)

    private String username;
    private String password;
    private UserType tipoUtente;

    private static final long serialVersionUID = 1L;

    protected User() {
        //COSTRUTTORE User
    }

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
