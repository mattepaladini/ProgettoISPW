package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.List;

public class CardCatalogDAODemo implements CardCatalogDAO {

    private static List<CardCatalog> cardCatalogs = null;

    @Override
    public List<CardCatalog> getAllCatalogs() {
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
    public CardCatalog getSeller(String username) {
        return null;
    }
}
