package com.example.progettoispw.pattern.observer;

import java.util.ArrayList;
import java.util.List;

public class NotificationSubject {

    private final List<NotificationObserver> observers = new ArrayList<>();

    @SuppressWarnings("java:S6548")
    private NotificationSubject() {}

    private static class InstanceHolder {
        private static final NotificationSubject INSTANCE = new NotificationSubject();
    }

    public static NotificationSubject getInstance() {
        return InstanceHolder.INSTANCE;
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
