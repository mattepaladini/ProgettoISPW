package com.example.progettoispw.pattern.observer;

import java.util.Observer;

public interface PriceSubject {


    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
