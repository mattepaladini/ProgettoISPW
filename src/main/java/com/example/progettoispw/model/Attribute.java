package com.example.progettoispw.model;

public enum Attribute {
    LUCE,
    OSCURITA,
    TERRA,
    ACQUA,
    FUOCO;

    public static Attribute fromString(String value){
        for (Attribute a : Attribute.values()) {
            if (a.name().equalsIgnoreCase(value)) {
                return a;
            }
        }
        throw new IllegalArgumentException("Gradazione non valida: "+value);
    }
}
