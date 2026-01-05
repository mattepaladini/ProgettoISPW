package com.example.progettoispw.Session;

import com.example.progettoispw.bean.UserBean;
import model.User;

public class SessionManager {

    private static SessionManager instance = null;
    private User loggedUser;

    // Costruttore privato per impedire 'new SessionManager()'
    private SessionManager() {}

    // Metodo per ottenere l'istanza (Singleton)
    public static SessionManager getInstance() {
        if (instance == null) {
            instance = new SessionManager();
        }
        return instance;
    }

    public void login(User user) {
        loggedUser = user;
    }

    public User getLoggedUser() {
        return this.loggedUser;
    }

    public void logout() {
        this.loggedUser = null;
    }

    //TODO ---> cambia il tipo che restituisce in getShoppingCart
    public void getShoppingCart(){

    }

}
