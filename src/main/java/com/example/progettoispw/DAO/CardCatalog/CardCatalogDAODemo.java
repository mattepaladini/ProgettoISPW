package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.ArrayList;
import java.util.List;

public class CardCatalogDAODemo implements CardCatalogDAO {

    private static List<CardCatalog> cardCatalogs = new ArrayList<>();

    @Override
    public List<CardCatalog> getAllCatalogs() {
        return cardCatalogs;
    }

    @Override
    public void addCatalog(CardCatalog catalog) {
        cardCatalogs.add(catalog);
    }

    @Override
    public void removeCard(Card card, String sellerName) {
        for(CardCatalog c : cardCatalogs) {
            if(c.getSeller().getSellerName().equals(sellerName)){
                c.removeCollectableCard(card);
            }
        }
    }

    @Override
    public void addCard(Card card, Seller sellerName) {
            for(CardCatalog catalog : cardCatalogs) {
                if(catalog.getSeller().equals(sellerName)) {
                    catalog.addCollectableCard(card);
                }
            }
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {
        for(CardCatalog catalogs : cardCatalogs) {
            if(catalogs.getSeller().equals(seller)) {
                return catalogs;
            }
        }
        return null;
    }
}
