package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAODemo;
import com.example.progettoispw.DAO.Order.OrderDAO;
import com.example.progettoispw.DAO.Order.OrderDAODemo;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.DAO.User.UserDAODemo;

public class DAOFactoryDemo extends DAOFactory{
    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        return new CardCatalogDAODemo();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAODemo();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new OrderDAODemo();
    }
}
