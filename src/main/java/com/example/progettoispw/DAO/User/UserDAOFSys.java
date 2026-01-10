package com.example.progettoispw.DAO.User;

import com.example.progettoispw.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOFSys implements UserDAO {

    private List<User> memoryUser = null;

    private static final String USER_FILE = "user.dat";

    @Override
    public List<User> getAllUsers() {

        if(memoryUser == null) {
            memoryUser = loadUsers();
        }
        return memoryUser;
    }

    @Override
    public void addUser(User user) {
        List<User> users = getAllUsers();
        users.add(user);
        saveData();
    }


    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean logWithPSW(String password) {
        return false;
    }


    //HELPER PER CARICARE I DATI DALLA MEMORIA
    public List<User> loadUsers() {

        File file = new File(USER_FILE);

        if(!file.exists()){
            return new ArrayList<>();
        }

        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (List<User>) ois.readObject();

        } catch (ClassNotFoundException | IOException e) {
            throw new RuntimeException(e);
        }

    }


    //HELPER PER SALVARE LE MODIFICHE APPORTATE
    public void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USER_FILE))) {
            oos.writeObject(memoryUser);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
