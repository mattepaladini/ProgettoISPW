package com.example.progettoispw.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {

    private static ConnectionFactory instance;
    private Connection connection;

    protected ConnectionFactory() {}

    public static ConnectionFactory getInstance() {
        if (instance == null) {
            instance = new ConnectionFactory();
            return instance;
        }
        return instance;
    }

    public Connection getConnection(){
        if(connection == null){
            try (InputStream input = new FileInputStream("resources/db.properties")) {
                Properties properties = new Properties();
                properties.load(input);

                String connection_url = properties.getProperty("CONNECTION_URL");
                String user = properties.getProperty("USER");
                String pass = properties.getProperty("PASS");

                connection = DriverManager.getConnection(connection_url, user, pass);
            } catch (IOException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }


}
