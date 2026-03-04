package com.example.progettoispw.dao.user;

import com.example.progettoispw.model.User;

import java.util.List;

public interface UserDAO {

    List<User> getAllUsers();
    void addUser(User user);
    User getUserByUsername(String username);
    boolean logWithPSW(String Username, String password);
}
