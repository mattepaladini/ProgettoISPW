package com.example.progettoispw.DAO;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class QueryLoader {

    private static final Properties queries = new Properties();

    static{
        try{
            queries.load(new FileInputStream("queries.properties"));
        }catch(IOException e){
            throw new RuntimeException(e);
        }
    }

    public static String getQuery(String query){
        return queries.getProperty(query);
    }
}
