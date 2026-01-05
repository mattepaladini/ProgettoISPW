package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAODB;
import com.example.progettoispw.DAO.Order.OrderDAO;
import com.example.progettoispw.DAO.Order.OrderDAODB;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.DAO.User.UserDAODB;

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
