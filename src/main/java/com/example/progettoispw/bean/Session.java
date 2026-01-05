package com.example.progettoispw.bean;

public class Session {

    private static Session instance = null;
    private UserBean loggedUser;

    // Costruttore privato per impedire 'new Session()'
    private Session() {}

    // Metodo per ottenere l'istanza (Singleton)
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }

    // Metodi per gestire l'utente loggato
    public void setLoggedUser(UserBean user) {
        this.loggedUser = user;
    }

    public UserBean getLoggedUser() {
        return this.loggedUser;
    }

    public void logout() {
        this.loggedUser = null;
    }

}
