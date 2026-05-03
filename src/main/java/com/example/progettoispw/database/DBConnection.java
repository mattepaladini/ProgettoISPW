package com.example.progettoispw.database;


import com.example.progettoispw.exception.DatabaseOperationException;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static  DBConnection instance=null;
    private Connection conn;

    Properties prop= new Properties();

    private DBConnection() throws DatabaseOperationException {
        try(InputStream input = DBConnection.class.getResourceAsStream("/config/db.properties")){

            prop.load(input);
            String connectionURL = prop.getProperty("CONNECTION_URL");
            String user = prop.getProperty("USER");
            String password = prop.getProperty("PASSWORD");

            conn = DriverManager.getConnection(connectionURL,user,password);

        } catch (Exception e) {
            throw new DatabaseOperationException(e.getMessage());
        }
    }

    public static DBConnection getInstance(){
        if(instance == null){
            synchronized (DBConnection.class){

                        instance = new DBConnection();

            }

        }
        return instance;
    }

    public  Connection getConnection() {
        return conn;
    }
}
