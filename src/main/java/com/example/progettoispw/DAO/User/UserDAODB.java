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

public class UserDAODB implements UserDAO {
    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<User>();

        String query = "{CALL getAllUsers()}";
        try(Connection conn = DBConnection.getConnection();
            CallableStatement stmt = (CallableStatement) conn.prepareCall(query)){

            if(stmt.execute()){
                try{
                    ResultSet rs = stmt.getResultSet();
                    while(rs.next()){
                        UserBean u = new UserBean();
                        u.setUsername(rs.getString("username"));
                        u.setPassword(rs.getString("password"));
                        u.setUsertype(UserType.valueOf(rs.getString("tipo_utente")));

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

        return users;
    }

    @Override
    public void addUser(User user) {

    }

    @Override
    public User getUserByUsername(String username) {
        return null;
    }

    @Override
    public boolean logWithPSW(String Username, String password) {
        return false;
    }
}
