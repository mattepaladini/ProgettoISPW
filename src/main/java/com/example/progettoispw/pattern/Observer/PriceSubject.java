package com.example.progettoispw.pattern.Observer;

import java.util.Observer;

public interface PriceSubject {


    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers();
}
