package com.example.progettoispw.database;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;

public class DBConnection {

    private static DBConnection instance;
    private Connection conn;

    private static final Logger logger = Logger.getLogger(DBConnection.class.getName());

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
                logger.severe(e.getMessage());
            }
        }

        return conn;
    }
}
