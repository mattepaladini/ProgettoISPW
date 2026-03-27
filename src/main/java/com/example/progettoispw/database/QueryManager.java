package com.example.progettoispw.database;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueryManager {

    private static final Properties properties = new Properties();
    private static final Logger log = Logger.getLogger(QueryManager.class.getName());

    //questo blocco viene eseguito una volta sola
    static {

        try (InputStream input = QueryManager.class.getClassLoader().getResourceAsStream("query/queries.properties")) {

            if (input == null) {
                log.log(Level.SEVERE, "File queries.properties non trovato!");
            } else {
                properties.load(input); // Carica tutte le query in RAM all'istante
            }

        } catch (IOException ex) {
            log.log(Level.SEVERE, "Errore nel caricamento delle query: {0}", ex.getMessage());
        }
    }

    // Costruttore privato
    private QueryManager() {
        throw new IllegalStateException("Utility class");
    }

    public static String getQuery(String key) {
        return properties.getProperty(key);
    }



}
