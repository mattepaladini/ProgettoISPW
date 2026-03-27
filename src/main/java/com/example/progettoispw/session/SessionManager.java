package com.example.progettoispw.session;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;

public class SessionManager {

    //scelta di progetto ---> il carrello viene legato al ciclo di vita dell'utente all'interno del sistema
    //                        quando eseguo il logout pulisco il carrello

    private static SessionManager instance = null;
    private User loggedUser;

    private List<Card> cart = new ArrayList<>();

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

    public void setLoggedUser(User user) {
        loggedUser = user;
    }

    public User getLoggedUser() {
        return loggedUser;
    }

    //logout viene eseguito a prescindere che l'utente chiuda direttamente l'eseguibile o che lo faccia manualmente
    public void logout() {
        this.cart.clear();      //pulisco il carrello se l'utente fa logout
        this.loggedUser = null;
    }


    public void addCard(Card card) {
        this.cart.add(card);
    }

    public void removeCard(Card card) {
        this.cart.removeIf(c -> c.getNome().equals(card.getNome()) &&
                c.getVenditore().equals(card.getVenditore()));
    }

    public List<Card> getShoppingCart(){
        return this.cart;
    }

    public void clearShoppingCart() {
        this.cart.clear();
    }


}
