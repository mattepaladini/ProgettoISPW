package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAOFSys;
import com.example.progettoispw.dao.follower.FollowerDAO;
import com.example.progettoispw.dao.notification.NotificationDAO;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.order.OrderDAOFsys;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.dao.user.UserDAOFSys;

public class DAOFactoryFSys extends DAOFactory {

    private CardCatalogDAOFSys cardCatalogDAOFSys;
    private UserDAOFSys userDAOFSys;
    private OrderDAOFsys orderDAOFSys;

    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        if(cardCatalogDAOFSys == null){
            cardCatalogDAOFSys = new CardCatalogDAOFSys();
        }
        return cardCatalogDAOFSys;
    }

    @Override
    public UserDAO getUserDAO() {
        if(userDAOFSys == null){
            userDAOFSys = new UserDAOFSys();
        }
        return userDAOFSys;
    }

    @Override
    public OrderDAO getOrderDAO() {
        if(orderDAOFSys == null){
            orderDAOFSys = new OrderDAOFsys();
        }
        return orderDAOFSys;
    }

    @Override
    public NotificationDAO getNotificationDAO(){
        return null;
    }

    @Override
    public FollowerDAO getFollowerDAO(){
        return null;
    }
}
