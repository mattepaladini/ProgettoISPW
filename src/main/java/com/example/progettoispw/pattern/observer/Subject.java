package com.example.progettoispw.pattern.observer;

//Interfaccia che l'oggetto osservato dovrà implementare

public interface Subject {

    void attach(PriceObserver observer);
    void notifyObservers();
    void detachAll();

}
