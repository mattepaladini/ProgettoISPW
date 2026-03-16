package com.example.progettoispw.model;

import com.example.progettoispw.pattern.observer.PriceObserver;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Card implements Serializable {

    private String nome;
    private Float prezzoAttuale;
    private Gradazione gradazione;
    private String venditore;
    private int livello;
    private Attribute attributo;
    private Type tipo;

    private transient List<PriceObserver> observers;    //transient serve per ignorare questa lista quando memorizzo su file o db


    public Card(String nome, Float prezzoAttuale, Gradazione gradazione, String venditore, int livello, Attribute attributo, Type tipo) {
        this.nome = nome;
        this.prezzoAttuale = prezzoAttuale;
        this.gradazione = gradazione;
        this.venditore = venditore;
        this.livello = livello;
        this.attributo = attributo;
        this.tipo = tipo;
    }


    public Card(String nome, Float prezzo){
        this.nome = nome;
        this.prezzoAttuale = prezzo;
    }

    //costruttore usato per memorizzare nel file "orders.txt" le informazioni delle carte ordinate
    public Card(String nome, String venditore){
        this.nome = nome;
        this.venditore = venditore;
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

    public void setPrezzoAttuale(Float prezzoAttuale){
        this.prezzoAttuale = prezzoAttuale;
        notifyObservers(); // Notifica tutti gli iscritti!
    }

    public Gradazione getGradazione(){
        return this.gradazione;
    }

    public String getVenditore(){
        return this.venditore;
    }


    public int getLivello(){return this.livello;}

    public Attribute getAttributo(){return this.attributo;}

    public Type getTipo(){return this.tipo;}

    //Metodi usati per iscriversi al pattern Observer

    // 1. Metodo per Iscriversi
    public void attach(PriceObserver observer) {
        if (this.observers == null) {
            this.observers = new ArrayList<>();
        }
        if (!this.observers.contains(observer)) {
            this.observers.add(observer);
        }
    }

    //2. Metodo per Disiscriversi
    public void detach(PriceObserver observer) {
        if (this.observers != null) {
            this.observers.remove(observer);
        }
    }

    // 4. La Notifica
    private void notifyObservers() {
        if (this.observers == null) return;

        for (PriceObserver obs : this.observers) {
            obs.updatePrice(this);
        }
    }

}
