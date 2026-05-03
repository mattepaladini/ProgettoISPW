package com.example.progettoispw.model;

public enum Type {
    MOSTRO,
    MAGIA,
    TERRENO,
    TRAPPOLA;

    public static Type fromString(String value){
        for (Type t : Type.values()) {
            if (t.name().equalsIgnoreCase(value)) {
                return t;
            }
        }
        throw new IllegalArgumentException("Tipo non valido: "+value);
    }
}
