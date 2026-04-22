package com.example.progettoispw.exception;

public enum DatabaseExceptionMessages {

    REMOVE_CARD_ERROR("Error during remove card on DB"),
    CATA_CREATE_ERROR("Impossible to create the catalog"),
    ADD_CARD_ERROR("Impossible to create or add a card"),
    UPDATE_CARD_ERROR("Impossible update price caard"),
    FINDA_CARD_ERROR("Impossible to find a card"),;


    private final String message;

    //Costruttore per messaggi dinamici e personalizzati
    DatabaseExceptionMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
