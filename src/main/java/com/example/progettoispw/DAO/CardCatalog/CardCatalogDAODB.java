package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.DAO.ConnectionFactory;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.sql.Connection;
import java.util.List;

public class CardCatalogDAODB implements CardCatalogDAO {

    //private Connection conn;


    @Override
    public List<CardCatalog> getAllCatalogs() {

        Connection conn = ConnectionFactory.getInstance().getConnection();



        return List.of();
    }

    @Override
    public void addCatalog(CardCatalog catalog) {

    }

    @Override
    public void removeCard(Card card, String sellerName) {

    }

    @Override
    public void addCard(Card card, Seller sellerName) {

    }


    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {
        return null;
    }
}
