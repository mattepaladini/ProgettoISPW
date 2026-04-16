package com.example.progettoispw.exception;

public enum DatabaseExceptionMessages {

    REMOVE_CARD_ERROR("Errore cancellazione carta nel DB"),
    CATA_CREATE_ERROR("Impossibile creare il catalogo"),
    ADD_CARD_ERROR("Impossibile creare o aggiungere la carta"),
    UPDATE_CARD_ERROR("Impossibile modificare il prezzo  della carta"),
    FINDA_CARD_ERROR("Impossibile trovare la carta");


    private final String message;

    //Costruttore per messaggi dinamici e personalizzati
    DatabaseExceptionMessages(String message){
        this.message = message;
    }

    public String getMessage(){
        return message;
    }
}
