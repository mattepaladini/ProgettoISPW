package com.example.progettoispw.exception;

public enum InvalidInputMessages {

    LOGIN_FAIL("Username e/o password errati"),
    REGISTRATION_FAIL("Username e/o password mancanti"),
    UPDATE_FAIL("Prezzo inserito non valido"),
    SEARCH_CARD_FAIL("Nome carta mancante");

    private final String message;
    InvalidInputMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
