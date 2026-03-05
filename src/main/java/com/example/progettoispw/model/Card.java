package com.example.progettoispw.model;

import com.example.progettoispw.pattern.Observer.PriceObserver;
import com.example.progettoispw.pattern.Observer.PriceSubject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Observer;

public class Card implements Serializable {


    //private int id;
    private String nome;
    private Float prezzoAttuale;
    //private List<Float> storicoPrezzi;
    private Gradazione gradazione;
    private String venditore;
    private int livello;
    private Attribute attributo;
    private Type tipo;

    private transient List<PriceObserver> observers;    //transient serve per ignorare questa lista quando memorizzo su file o db


    // Da definire come determinare gli id delle carte.
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
}
