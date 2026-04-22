package com.example.progettoispw.exception;

public enum InvalidInputMessages {

    LOGIN_FAIL("Username and/or password wrong"),
    REGISTRATION_FAIL("Username and/or password missing"),
    UPDATE_FAIL("Price added not valid"),
    SEARCH_CARD_FAIL("Missing card name");

    private final String message;
    InvalidInputMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

}
