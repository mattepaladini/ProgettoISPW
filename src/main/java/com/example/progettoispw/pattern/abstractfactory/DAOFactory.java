package com.example.progettoispw.pattern.abstractfactory;

import com.example.progettoispw.dao.PersistenceType;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.dao.user.UserDAO;

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
