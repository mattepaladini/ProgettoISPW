package com.example.progettoispw.DAO.User;

import model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAODemo implements UserDAO {

    private static List<User> users = new ArrayList<User>();

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
}
