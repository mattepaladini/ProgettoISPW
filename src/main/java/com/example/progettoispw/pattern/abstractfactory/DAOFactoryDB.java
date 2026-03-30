package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAODB;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.order.OrderDAODB;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.dao.user.UserDAODB;

public class DAOFactoryDB extends DAOFactory {

    //introdotti perchè sfruttando internamente dei metodi per capire se ho aggiornato le informazioni sulla RAM,
    //se non li avessi usati avrei reso inutile la cache in quanto avrei ricaricato da 0 a ogni chiamata di get
    private CardCatalogDAODB cardCatalogDAODB;
    private UserDAODB userDAODB;
    private OrderDAODB orderDAODB;

    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        if(cardCatalogDAODB == null){
            cardCatalogDAODB = new CardCatalogDAODB();
        }
        return cardCatalogDAODB;
    }

    @Override
    public UserDAO getUserDAO() {
        if(userDAODB == null){
            userDAODB = new UserDAODB();
        }
        return userDAODB;
    }

    @Override
    public OrderDAO getOrderDAO() {
        if(orderDAODB == null){
            orderDAODB = new OrderDAODB();
        }
        return orderDAODB;
    }
}
