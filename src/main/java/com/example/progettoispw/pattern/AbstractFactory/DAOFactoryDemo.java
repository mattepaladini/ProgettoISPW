package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAODemo;
import com.example.progettoispw.DAO.Order.OrderDAO;
import com.example.progettoispw.DAO.Order.OrderDAODemo;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.DAO.User.UserDAODemo;

public class DAOFactoryDemo extends DAOFactory{

    private static CardCatalogDAO catalogDAO;
    private static UserDAO userDAO;
    private static OrderDAO orderDAO;

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
}
