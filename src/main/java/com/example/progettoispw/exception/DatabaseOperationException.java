package com.example.progettoispw.exception;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(String message) {
        super(message);
    }
}

//TODO GESTISCI CHE SE SEI IN CLI MANDA SULLA CONSOLE ALTRIMENTI MANDA POPUP
