package com.example.progettoispw.DAO.User;

import model.User;

import java.util.List;

public class UserDAODB implements UserDAO {
    @Override
    public List<User> getAllUsers() {
        return List.of();
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean logWithPSW(String password) {
        return false;
    }
}
