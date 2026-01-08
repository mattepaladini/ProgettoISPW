package com.example.progettoispw.model;

import java.util.ArrayList;
import java.util.List;

public class Card {

    private int id;
    private String nome;
    private Float prezzoAttuale;
    private List<Float> storicoPrezzi;
    private Gradazione gradazione;
    private User venditore;

    public Card(String nome, Float prezzoAttuale, Gradazione gradazione, User venditore, int id) {
        this.id = id;
        this.nome = nome;
        this.prezzoAttuale = prezzoAttuale;
        this.gradazione = gradazione;
        this.venditore = venditore;
        this.storicoPrezzi = new ArrayList<Float>();
        this.storicoPrezzi.add(prezzoAttuale);
    }

    public Float getPrezzoAttuale(){
        return this.prezzoAttuale;
    }

    public List<Float> getStoricoPrezzi(){
        return this.storicoPrezzi;
    }

    public Gradazione getGradazione(){
        return this.gradazione;
    }

    public User getVenditore(){
        return this.venditore;
    }

    public int getId(){
        return this.id;
    }
}
