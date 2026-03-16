package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAODB;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.order.OrderDAODB;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.dao.user.UserDAODB;

public class DAOFactoryDB extends DAOFactory {
    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        return new CardCatalogDAODB();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAODB();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new OrderDAODB();
    }
}
