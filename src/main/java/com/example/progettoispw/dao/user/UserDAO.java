package com.example.progettoispw.dao.user;

import com.example.progettoispw.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();       //restituisce tutti gli utenti salvati
    void addUser(User user);        //aggiunge l'utente user
    void deleteUser(User user);     //rimuove l'utente user
    User getUserByUsername(String username);        //restituisce l'utente memorizzato come username
    boolean logWithPSW(String username, String password);       //restituisce vero se la coppia (username, password) è stata già registrata

}
