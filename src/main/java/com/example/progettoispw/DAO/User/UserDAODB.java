package com.example.progettoispw.DAO.User;

import com.example.progettoispw.DataBase.DBConnection;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.mysql.cj.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDAODB extends UserDAODemo implements UserDAO {

    private boolean isLoaded = false;

    @Override
    public List<User> getAllUsers() {

        loadAllUsers();
        return super.getAllUsers();
    }

    @Override
    public void addUser(User user) {

        loadAllUsers();

        saveOnDB(user);

        super.addUser(user);

    }

    @Override
    public User getUserByUsername(String username) {
        loadAllUsers();
        return super.getUserByUsername(username);
    }

    @Override
    public boolean logWithPSW(String Username, String password) {

        loadAllUsers();
        return super.logWithPSW(Username, password);
    }


    public void loadAllUsers(){

        if(!isLoaded) {
            String query = "{CALL GetAllUsers()}";
            try (Connection conn = DBConnection.getConnection();
                 CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

                if (stmt.execute()) {
                    try {
                        ResultSet rs = stmt.getResultSet();
                        while (rs.next()) {
                            UserBean u = new UserBean();
                            u.setUsername(rs.getString("username"));
                            u.setPassword(rs.getString("psw"));
                            u.setUsertype(UserType.valueOf(rs.getString("tipo_utente").toUpperCase()));

                            User user = new User(u.getUsername(), u.getPassword(), u.getUsertype());

                            users.add(user);
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalArgumentException e) {
                        throw new RuntimeException(e);
                    }
                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void saveOnDB(User user) {

        String new_username = user.getUsername();
        String new_password = user.getPassword();
        String new_tipo = String.valueOf(user.getTipoUtente());

        String query ="{CALL AddUser (?, ?,?)}";

        try (Connection conn = DBConnection.getConnection();
             CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            stmt.setString(1, new_username);
            stmt.setString(2, new_password);
            stmt.setString(3, new_tipo);
            stmt.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
