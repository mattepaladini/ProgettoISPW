package com.example.progettoispw.DataBase;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    // 1. INSERISCI QUI IL NOME DEL TUO DATABASE (quello che vedi su Workbench)
    private static final String DB_URL = "jdbc:mysql://localhost:3306/CardMarketISPW";

    // 2. INSERISCI IL TUO UTENTE (di solito è "root")
    private static final String USER = "root";

    // 3. INSERISCI LA TUA PASSWORD DI MYSQL
    private static final String PASS = "Matteo2004$";

    public static Connection getConnection() {
        Connection conn = null;
        try {
            // Verifica che il driver sia caricato (opzionale con le nuove versioni ma sicuro)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Tenta la connessione
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }
}
