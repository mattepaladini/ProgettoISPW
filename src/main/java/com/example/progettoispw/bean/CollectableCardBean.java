package com.example.progettoispw.bean;

import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Gradazione;
import com.example.progettoispw.model.Type;

public class CollectableCardBean {

    private int id;
    private String nomeCarta;
    private float prezzoCorrente;
    private Gradazione gradazione;
    private int livello;
    private Attribute attributo;
    private Type tipo;
    private String venditore;

    //costruttore
    public CollectableCardBean() {
        this.id=0;
    }

    public CollectableCardBean(String nomeCarta, float prezzoCorrente) {
        this.nomeCarta = nomeCarta;
        this.prezzoCorrente = prezzoCorrente;
    }

    public CollectableCardBean(int id, String nomeCarta, float prezzoCorrente, Gradazione gradazione){
        this.id=id;
        this.nomeCarta=nomeCarta;
        this.prezzoCorrente=prezzoCorrente;
        this.gradazione=gradazione;
    }

    public String getNomeCarta() {return nomeCarta;}
    public void setNomeCarta(String nomeCarta) {
        if (nomeCarta != null) {
            this.nomeCarta = nomeCarta;
        }
    }


    public float getPrezzoCorrente() {return prezzoCorrente;}
    public void setPrezzoCorrente(float prezzo) {
        if(prezzo>0){
            this.prezzoCorrente=prezzo;
        }
    }

    public Gradazione getGradazione() {return gradazione;}
    public void setGradazione(Gradazione gradazione) {
        if(gradazione!=null){
            this.gradazione=gradazione;
        }
        }

    public int getLivello() {return livello;}
    public void setLivello(int livello) {
        if(livello>0){
            this.livello=livello;
        }
    }

    public Attribute getAttributo() {return attributo;}
    public void setAttributo(Attribute attributo) {
        if(attributo!=null){
            this.attributo=attributo;
        }
    }

    public Type getTipo() {return tipo;}
    public void setTipo(Type tipo) {this.tipo=tipo;}

    public String getVenditore() {return venditore;}
    public void setVenditore(String venditore) {this.venditore=venditore;}

    public int getId() {return id;}
    public void setId(int id) {this.id = id;}
}
