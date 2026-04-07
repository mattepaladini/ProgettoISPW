package com.example.progettoispw.bean;


public class NotificationBean {

    // Campi privati: contengono solo i dati essenziali per la View
    private int id;
    private String recipient;
    private String sender;
    private String message;
    private String date;
    private boolean read;


    public NotificationBean() {
        // Costruttore vuoto
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getSender() {
        return sender;
    }
    public void setSender(String seller) {
        this.sender = seller;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean isRead() {
        return read;
    }
    public void setRead(boolean read) {
        this.read = read;
    }
}
