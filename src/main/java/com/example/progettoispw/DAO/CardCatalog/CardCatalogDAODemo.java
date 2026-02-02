package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;

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
    public void removeCard(Card card, User sellerName) {

            cardCatalogs = getAllCatalogs();
            for(CardCatalog catalog : cardCatalogs) {
                if(catalog.getSeller().equals(sellerName)) {
                    cardCatalogs.remove(card);
                }
            }


    }

    @Override
    public void addCard(Card card, User sellerName) {
        cardCatalogs = getAllCatalogs();

            for(CardCatalog catalog : cardCatalogs) {
                if(catalog.getSeller().getSellerName().equals(sellerName.getUsername())) {
                    catalog.addCollectableCard(card);
                }
            }


    }

    @Override
    public void updatePrice(Card card) {
        cardCatalogs = getAllCatalogs();
        for(CardCatalog catalog : cardCatalogs) {}
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
