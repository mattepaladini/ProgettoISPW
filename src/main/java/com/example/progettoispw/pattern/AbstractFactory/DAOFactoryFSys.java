package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAOFSys;
import com.example.progettoispw.DAO.Order.OrderDAO;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.DAO.User.UserDAOFSys;

public class DAOFactoryFSys extends DAOFactory {

    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        return new CardCatalogDAOFSys();
    }

    @Override
    public UserDAO getUserDAO() {
        return null;
    }

    @Override
    public OrderDAO getOrderDAO() {
        return null;
    }
}
