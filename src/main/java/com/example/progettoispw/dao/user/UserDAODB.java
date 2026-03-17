package com.example.progettoispw.dao.user;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.mysql.cj.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
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
    public boolean logWithPSW(String username, String password) {

        loadAllUsers();
        return super.logWithPSW(username, password);
    }


    public void loadAllUsers(){

        if(!isLoaded) {
            String query = "{CALL GetAllUsers()}";
            Connection conn = DBConnection.getInstance().getConnection();
            try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

                if (stmt.execute()) {
                    executeLoadAllUsers(stmt);
                }

            } catch (SQLException e) {
                ErrorHandler.show(new DatabaseOperationException(e.getMessage()));
            }
        }
    }

    private void executeLoadAllUsers(CallableStatement stmt){
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

        } catch (RuntimeException | SQLException e) {
            ErrorHandler.show(new DatabaseOperationException(e.getMessage()));
        }
    }

    public void saveOnDB(User user) {

        String newUsername = user.getUsername();
        String newPassword = user.getPassword();
        String newTipo = String.valueOf(user.getTipoUtente());

        String query ="{CALL AddUser (?, ?,?)}";
        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            stmt.setString(1, newUsername);
            stmt.setString(2, newPassword);
            stmt.setString(3, newTipo);
            stmt.execute();

        } catch (SQLException e) {
            ErrorHandler.show(new DatabaseOperationException(e.getMessage()));
        }


    }
}
