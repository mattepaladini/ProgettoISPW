package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAOFSys;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.order.OrderDAOFsys;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.dao.user.UserDAOFSys;

public class DAOFactoryFSys extends DAOFactory {

    @Override
    public CardCatalogDAO getCardCatalogDAO() {
        return new CardCatalogDAOFSys();
    }

    @Override
    public UserDAO getUserDAO() {
        return new UserDAOFSys();
    }

    @Override
    public OrderDAO getOrderDAO() {
        return new OrderDAOFsys();
    }
}
