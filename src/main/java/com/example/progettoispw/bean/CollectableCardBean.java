package com.example.progettoispw.bean;

public class CollectableCardBean {

    private int id;
    private String nomeCarta;
    private float prezzoCorrente;
    private String gradazione;
    private int livello;
    private String attributo;
    private String tipo;

    //la validazione dei dati inseriti la deleghiamo al controller logico

    //costruttore
    public CollectableCardBean() {
        this.id=0;
    }

    //valuta se serve veramente
    public CollectableCardBean(int id, String nomeCarta, float prezzoCorrente, String gradazione){}

    public String getNomeCarta() {return nomeCarta;}
    public void setNomeCarta(String nomeCarta) {this.nomeCarta = nomeCarta;}

    public float getPrezzoCorrente() {return prezzoCorrente;}
    public void setPrezzoCorrente(float prezzoSX) {this.prezzoCorrente = prezzoSX;}

    public String getGradazione() {return gradazione;}
    public void setGradazione(String gradazione) {this.gradazione = gradazione;}

    public int getLivello() {return livello;}
    public void setLivello(int livello) {this.livello = livello;}

    public String getAttributo() {return attributo;}
    public void setAttributo(String attributo) {this.attributo = attributo;}

    public String getTipo() {return tipo;}
    public void setTipo(String tipo) {this.tipo = tipo;}





}
