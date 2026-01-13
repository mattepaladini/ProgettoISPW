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
            cardCatalogs.remove(card);
    }

    @Override
    public void addCard(Card card, Seller sellerName) {
        cardCatalogs = getAllCatalogs();

            for(CardCatalog catalog : cardCatalogs) {
                if(catalog.getSeller().getSellerName().equals(sellerName.getSellerName())) {
                    catalog.addCollectableCard(card);
                }
            }


    }

    @Override
    public CardCatalog getSeller(String username) {
        return null;
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {

        for(CardCatalog catalogs : cardCatalogs) {
            if(catalogs.getSeller().getSellerName().equals(seller.getSellerName())) {
                return catalogs;
            }
        }

        return null;
    }
}
