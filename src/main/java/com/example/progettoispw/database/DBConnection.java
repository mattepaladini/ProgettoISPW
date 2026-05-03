package com.example.progettoispw.database;


import com.example.progettoispw.exception.DatabaseOperationException;

import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBConnection {

    private static  DBConnection instance=null;
    private Connection conn;

    Properties prop= new Properties();

    private DBConnection() throws DatabaseOperationException {
        try(FileInputStream dbInfoFile = new FileInputStream("src/main/resources/config/db.properties")){

            prop.load(dbInfoFile);
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
