package com.example.progettoispw.model;

public enum Gradation {
    PERFETTO,
    BUONO,
    USATO,
    SCARSO;

    public static Gradation fromString(String value){
        for (Gradation g : Gradation.values()) {
            if (g.name().equalsIgnoreCase(value)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Gradation non valida: "+value);
    }
}