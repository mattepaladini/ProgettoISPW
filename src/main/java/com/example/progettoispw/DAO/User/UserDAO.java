package com.example.progettoispw.DAO.User;

import model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();
    void addUser(User user);
    User getUserByUsername(String username);
    boolean logWithPSW(String password);
}
