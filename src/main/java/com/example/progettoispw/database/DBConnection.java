package com.example.progettoispw.database;


import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.exception.DatabaseOperationException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static DBConnection instance;
    private Connection conn;

    private DBConnection(){
        this.conn = null;
    }

    public static DBConnection getInstance(){
        if(instance == null){
            instance = new DBConnection();
        }
        return instance;
    }

    public  Connection getConnection() {

        if(conn==null){
            try(InputStream input = new FileInputStream("src/main/resources/config/db.properties")){
                Properties prop = new Properties();
                prop.load(input);
                String dbUrl = prop.getProperty("CONNECTION_URL");
                String user = prop.getProperty("USER");
                String pass = prop.getProperty("PASSWORD");

                conn = DriverManager.getConnection(dbUrl, user, pass);

            }catch (IOException|SQLException e){
                ErrorHandler.show(new DatabaseOperationException(e.getMessage()));
            }
        }

        return conn;
    }
}
