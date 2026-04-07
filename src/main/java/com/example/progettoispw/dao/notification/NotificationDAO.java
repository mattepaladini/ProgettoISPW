package com.example.progettoispw.dao.notification;

import com.example.progettoispw.model.Notification;

import java.util.List;

public interface NotificationDAO {

    //salva una nuova notifica notification
    void saveNotification(Notification notification);

    //Recupera tutte le notifiche non lette destinate a destinatarioUsername.
    List<Notification> getUnreadNotifications(String destinatarioUsername);

    //Recupera lo storico completo delle notifiche di un utente.
    List<Notification> getAllNotifications(String destinatarioUsername);

    //Segna una notifica come letta (consumata dall'Observer)
    void markAsRead(int notificationId);
}
