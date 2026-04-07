package com.example.progettoispw.model;

public class Notification {

    private int id;
    private String message;
    private String date;
    private String recipient;   //destinatario
    private String sender;
    private boolean read;


    public Notification(String recipient, String sender, String message, String date) {
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
        this.date = date;
    }

    public Notification(int id, String recipient, String sender, String message, String date, boolean read) {
        this.id = id;
        this.recipient = recipient;
        this.sender = sender;
        this.message = message;
        this.date = date;
        this.read = read;
    }


    public int getId() {return id;}
    public void setId(int id) {this.id = id;}

    public String getMessage() {
        return message;
    }
    public String getDate() {
        return date;
    }

    public String getRecipient() {return recipient;}
    public String getSender() {return sender;}
    public boolean isRead() {return read;}
    public void setRead(boolean read) {this.read = read;}
}
