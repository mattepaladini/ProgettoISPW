package com.example.progettoispw.dao.user;

import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAODemo implements UserDAO {

    protected static List<User> users = new ArrayList<>();

    @Override
    public List<User> getAllUsers() {
        return users;
    }

    @Override
    public void addUser(User user) {
        users.add(user);
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
                return true;
            }
        }
        return false;
    }
}