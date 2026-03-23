package com.example.progettoispw.dao.user;

import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAODemo implements UserDAO {

    protected static List<User> users = new ArrayList<>();
    private static final Logger log = Logger.getLogger(UserDAODemo.class.getName());

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
        log.log(Level.INFO, "Utente aggiunto con successo");
    }

    @Override
    public void deleteUser(User user) {
        users.remove(user);
        log.log(Level.INFO, "Utente rimosso con successo");
    }

    @Override
    public User getUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public boolean logWithPSW(String username, String password) {
        for (User user : users) {
            if(user.getPassword().equals(password)) {
                log.log(Level.INFO, "Utente loggato con successo");
                return true;
            }
        }
        return false;
    }
}