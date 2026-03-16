package com.example.progettoispw.pattern.observer;

import java.util.Observer;

//TODO RIVEDI
public interface PriceSubject {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
