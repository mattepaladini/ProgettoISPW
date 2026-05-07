package com.example.progettoispw.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationSubject {

    private static volatile NotificationSubject instance;
    private final List<NotificationObserver> observers = new ArrayList<>();

    private NotificationSubject() {}

    public static NotificationSubject getInstance() {
        if (instance == null) {
            synchronized (NotificationSubject.class) {
                if (instance == null) {
                    instance = new NotificationSubject();
                }
            }

        }
        return instance;
    }

    //called by graphic controller
    public void attach(NotificationObserver observer) {
        observers.add(observer);
    }

    //called during logout
    public void detach(NotificationObserver observer) {
        observers.remove(observer);
    }

    public void detachAll() {
        observers.clear();
    }

    //called by ManageNotificationController
    public void notifyObservers(String message) {
        System.out.println("Observer registrati: " + observers.size());
        observers.forEach(o->o.onNotficationReceived(message));
    }
}
