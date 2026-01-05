package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.*;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.Order.OrderDAO;
import com.example.progettoispw.DAO.User.UserDAO;

public abstract class DAOFactory  {

    //VARIABILE GLOBALE
    protected static PersistenceType persistenceType;

    //VARIABILE PER SINGLETON
    private static DAOFactory instance = null;

    public static synchronized DAOFactory getInstance() {
        if (instance == null) {
            switch (persistenceType){
                case FSYS -> instance = new DAOFactoryFSys();
                case JDBC -> instance = new DAOFactoryDB();
                case DEMO -> instance = new DAOFactoryDemo();
                default -> throw new IllegalStateException("Unexpected value: " + persistenceType);
            }
        }
        return instance;
    }

    public static void setPersistenceType(PersistenceType type) {
        persistenceType = type;
    }

    public abstract CardCatalogDAO getCardCatalogDAO();

    public abstract UserDAO getUserDAO();

    public abstract OrderDAO getOrderDAO();
}
