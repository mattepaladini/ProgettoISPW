package model;

import java.util.List;

public class User {

    //private List<Carta> carrello;
    private String username;
    private String password;
    private String tipoUtente;

    public User(String username) {
        this.username = username;
    }
    private List<Order> ordini;

    public String getUsername() {
        return username;
    }
}
