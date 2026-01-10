package com.example.progettoispw.model;

public enum Gradazione {
    PERFETTO,
    BUONO,
    USATO,
    SCARSO;

    public static Gradazione fromString(String value){
        for (Gradazione g : Gradazione.values()) {
            if (g.name().equalsIgnoreCase(value)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Gradazione non valida: "+value);
    }
}