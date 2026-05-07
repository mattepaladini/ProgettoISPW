package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.NotificationBean;
import com.example.progettoispw.dao.follower.FollowerDAO;
import com.example.progettoispw.dao.notification.NotificationDAO;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.model.Notification;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.observer.NotificationSubject;

import java.time.LocalDate;
import java.util.List;

public class ManageNotificationsController {


    public List<NotificationBean> getUnreadNotifications(String username) {

        if(username == null || username.isBlank()){
            throw new InvalidInputException("username mancante");
        }
        NotificationDAO notificationDAO = DAOFactory.getInstance().getNotificationDAO();
        return notificationDAO.getUnreadNotifications(username).stream().map(this::toBean).toList();

    }

    public boolean followSeller(String buyer, String seller){
        FollowerDAO followerDAO = DAOFactory.getInstance().getFollowerDAO();
       if(followerDAO.isFollowing(buyer, seller)){
            return false;
        }

        followerDAO.follow(buyer, seller);

        NotificationDAO notificationDAO = DAOFactory.getInstance().getNotificationDAO();
        Notification notif = new Notification(seller, buyer, "L'utente "+buyer+" ha iniziato a seguirti", LocalDate.now().toString());
        notificationDAO.saveNotification(notif);
        return true;

    }

    public List<NotificationBean> getNotifications(String username) {
        if(username == null || username.isBlank()){
            throw new InvalidInputException("username mancante");
        }

        NotificationDAO notificationDAO = DAOFactory.getInstance().getNotificationDAO();

        return notificationDAO.getAllNotifications(username).stream().map(this::toBean).toList();
    }

    public void markAsRead(int notificationID) {
        if(notificationID <= 0){
            throw new InvalidInputException("notificationID non valido");
        }
        NotificationDAO notificationDAO = DAOFactory.getInstance().getNotificationDAO();
        notificationDAO.markAsRead(notificationID);
    }

    public boolean checkFollowStatus(String buyer, String seller) {
        FollowerDAO followerDAO = DAOFactory.getInstance().getFollowerDAO();
        return followerDAO.isFollowing(buyer, seller);
    }


    private  NotificationBean toBean(Notification notification) {
        NotificationBean bean = new NotificationBean();
        bean.setId(notification.getId());
        bean.setRecipient(notification.getRecipient());
        bean.setSender(notification.getSender());
        bean.setMessage(notification.getMessage());
        bean.setDate(notification.getDate());
        bean.setRead(notification.isRead());
        return bean;
    }

    void publishNotification(String seller, String message){
        FollowerDAO followerDAO = DAOFactory.getInstance().getFollowerDAO();
        NotificationDAO notificationDAO = DAOFactory.getInstance().getNotificationDAO();

        List<String> followers = followerDAO.getFollowers(seller);
        String data = LocalDate.now().toString();

        for(String buyer: followers){
            Notification notif = new Notification(buyer, seller, message, data);
            notificationDAO.saveNotification(notif);

            NotificationSubject.getInstance().notifyObservers(message);
        }
    }
}
