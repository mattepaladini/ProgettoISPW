package com.example.progettoispw.dao.notification;

import com.example.progettoispw.model.Notification;

import java.util.ArrayList;
import java.util.List;

public class NotificationDAODemo implements NotificationDAO {

    protected final List<Notification> notifications = new ArrayList<>();



    @Override
    public void saveNotification(Notification notification) {
        notifications.add(notification);
    }

    @Override
    public List<Notification> getUnreadNotifications(String senderUsername) {
        List<Notification> unreadNotifications = new ArrayList<>();
        for (Notification notif : notifications) {
            if(notif.getRecipient().equals(senderUsername) && !notif.isRead()) {
                unreadNotifications.add(notif);
            }
        }
        return unreadNotifications;
    }

    @Override
    public List<Notification> getAllNotifications(String senderUsername) {
        List<Notification> allNotifications = new ArrayList<>();
        for (Notification notif : notifications) {
            if(notif.getRecipient().equals(senderUsername)) {
                allNotifications.add(notif);
            }
        }
        return allNotifications;
    }

    @Override
    public void markAsRead(int notificationId) {
        for (Notification notif : notifications) {
            if(notif.getId() == notificationId) {
                notif.setRead(true);
                break;
            }
        }

    }
}
