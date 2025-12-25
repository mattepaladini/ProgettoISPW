package model;

import java.util.ArrayList;
import java.util.List;

public class Carta {
    private String nome;
    private Float prezzoAttuale;
    private List<Float> storicoPrezzi;
    private Gradazione gradazione;
    private Utente venditore;

    public Carta(String nome, Float prezzoAttuale, Gradazione gradazione, Utente venditore) {
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

    public Utente getVenditore(){
        return this.venditore;
    }
}
