package com.example.progettoispw.model;

import java.io.Serializable;

public class Card implements  Serializable {

    //scelta di progetto ---> ogni carta viene identificata in un catalogo con il suo nome in quanto si assume che
    //                        un venditore non possa avere in vendita altre carte con quel nome

    private String nome;
    private Float prezzoAttuale;
    private Gradazione gradazione;
    private String venditore;
    private int livello;
    private Attribute attributo;
    private Type tipo;

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
