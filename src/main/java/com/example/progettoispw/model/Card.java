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
    private int livello;
    private String attributo;
    private String tipo;

    // Da definire come determinare gli id delle carte.
    public Card(String nome, Float prezzoAttuale, Gradazione gradazione, User venditore, int id, int livello, String attributo, String tipo) {
        this.id = id;
        this.nome = nome;
        this.prezzoAttuale = prezzoAttuale;
        this.gradazione = gradazione;
        this.venditore = venditore;
        this.storicoPrezzi = new ArrayList<Float>();
        this.storicoPrezzi.add(prezzoAttuale);
        this.livello = livello;
        this.attributo = attributo;
        this.tipo = tipo;
    }

    /*
    N.B: TUTTI I GETTER SOTTOSTANTI SONO FONDAMENTALI PER MAPPARE LE ENTITA' RITORNATE DAL DAO IN BEAN DA POTER
         RITORNARE AL CONTROLLER GRAFICO "SellerCatalogGraphicController". L'INCAPSULAMENTO VIENE PRESERVATO PERCHE'
         MI STO LIMITANDO A LEGGERE LO STATO MENTRE LA LOGICA RIMANE ALL'INTERNO DEL CONTROLLER LOGICO
     */
    public String getNome() {
        return nome;
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

    public int getLivello(){return this.livello;}

    public String getAttributo(){return this.attributo;}

    public String getTipo(){return this.tipo;}
}
