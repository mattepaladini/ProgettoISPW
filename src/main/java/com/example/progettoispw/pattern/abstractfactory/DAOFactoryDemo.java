package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAODemo;
import com.example.progettoispw.dao.follower.FollowerDAO;
import com.example.progettoispw.dao.follower.FollowerDAODemo;
import com.example.progettoispw.dao.notification.NotificationDAO;
import com.example.progettoispw.dao.notification.NotificationDAODemo;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.order.OrderDAODemo;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.dao.user.UserDAODemo;

public class DAOFactoryDemo extends DAOFactory{

    private  CardCatalogDAO catalogDAO;
    private  UserDAO userDAO;
    private  OrderDAO orderDAO;
    private NotificationDAO notificationDAO;
    private FollowerDAO followerDAO;

    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        if(catalogDAO == null){
            catalogDAO = new CardCatalogDAODemo();
        }
        return catalogDAO;
    }

    @Override
    public UserDAO getUserDAO() {
        if(userDAO == null){
            userDAO = new UserDAODemo();
        }
        return userDAO;
    }

    @Override
    public OrderDAO getOrderDAO() {
        if(orderDAO == null){
            orderDAO = new OrderDAODemo();
        }
        return orderDAO;
    }

    @Override
    public NotificationDAO getNotificationDAO() {
        if(notificationDAO == null){
            notificationDAO = new NotificationDAODemo();
        }
        return notificationDAO;
    }

    @Override
    public FollowerDAO getFollowerDAO() {
        if(followerDAO == null){
            followerDAO = new FollowerDAODemo();
        }
        return followerDAO;
    }
}
