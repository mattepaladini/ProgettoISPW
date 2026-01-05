package com.example.progettoispw.pattern.AbstractFactory;

import com.example.progettoispw.DAO.CardCatalogDAO;
import com.example.progettoispw.DAO.CardCatalogDAODB;
import com.example.progettoispw.DAO.CardCatalogDAODemo;
import com.example.progettoispw.DAO.PersistenceType;

public class DAOFactory {

    //compito di variabile globale
    private static PersistenceType persistenceType;

    public static void setPersistenceType(PersistenceType type) {
        persistenceType = type;
    }

    public static CardCatalogDAO getCardCatalogDAO() {
        switch (persistenceType) {
            case JDBC -> {
                return new CardCatalogDAODB();
            }
            case FSYS -> {
                return new CardCatalogDAODemo();
            }
            default -> {throw new IllegalArgumentException("Tipo non supportato o inesistente");}
        }
    }

}
