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

    // 1. INSERISCI QUI IL NOME DEL TUO DATABASE (quello che vedi su Workbench)
    //private static final String DB_URL = "jdbc:mysql://localhost:3306/CardMarketISPW";

    // 2. INSERISCI IL TUO UTENTE (di solito è "root")
    //private static final String USER = "root";

    // 3. INSERISCI LA TUA PASSWORD DI MYSQL
    //private static final String PASS = "Matteo2004$";

    public  Connection getConnection() {

        if(conn==null){
            try(InputStream input = new FileInputStream("src/main/resources/config/db.properties")){
                Properties prop = new Properties();
                prop.load(input);
                String db_url = prop.getProperty("CONNECTION_URL");
                String user = prop.getProperty("USER");
                String pass = prop.getProperty("PASSWORD");

                conn = DriverManager.getConnection(db_url, user, pass);

            }catch (IOException|SQLException e){
                logger.severe(e.getMessage());
            }
        }

        /*
        Connection conn = null;
        try {
            // Verifica che il driver sia caricato (opzionale con le nuove versioni ma sicuro)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tenta la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }*/
        return conn;
    }
}
