package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import model.CardCatalog;
import model.Seller;

import java.util.List;

public class CardCatalogDAODB implements CardCatalogDAO {

    //private Connection conn;


    @Override
    public List<CardCatalog> getAllCatalogs() {
        return List.of();
    }

    @Override
    public void addCatalog(CardCatalog catalog) {

    }

    @Override
    public void removeCard(CollectableCardBean card, String sellerName) {

    }

    @Override
    public void addCard(CollectableCardBean card, Seller sellerName) {

    }

    @Override
    public CardCatalog getSeller(String username) {
        return null;
    }
}
