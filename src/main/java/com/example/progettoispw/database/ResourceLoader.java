package com.example.progettoispw.database;

import com.example.progettoispw.exception.FSysOperationException;
import com.example.progettoispw.exception.OperationFailedException;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceLoader {

    private ResourceLoader() throws OperationFailedException{
        throw new OperationFailedException("Operation failed");
    }

    public static Properties loadProperties(String resourcePath){
        Properties prop = new Properties();

        try(InputStream in = ResourceLoader.class.getResourceAsStream(resourcePath)){
            if(in == null){
                throw new FSysOperationException("Resource not found");
            }
            prop.load(in);
            return prop;

        }catch (IOException e){
            throw new FSysOperationException("Impossibile aprire il file");
        }
    }

}
