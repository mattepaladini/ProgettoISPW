package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;

public class CardCatalogDAODemo implements CardCatalogDAO {

    protected static List<CardCatalog> cardCatalogs = new ArrayList<>();

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
    public void addCard(Card card, String sellerName) {
        cardCatalogs = getAllCatalogs();

            for(CardCatalog catalog : cardCatalogs) {
                if(catalog.getSeller().getSellerName().equals(sellerName)) {
                    System.out.println("Carta aggiunta al catalogo di: "+catalog.getSeller().getSellerName());
                    catalog.addCollectableCard(card);
                }
            }


    }

    @Override
    public void updatePrice(String nomeCarta, String username, Float newPrice) {
        cardCatalogs = getAllCatalogs();
        for(CardCatalog catalog : cardCatalogs) {
            if(catalog.getSeller().getSellerName().equals(username)) {
                for(Card c : catalog.getCards()){
                    if(c.getNome().equals(nomeCarta)){
                        c.setPrezzoAttuale(newPrice);
                    }
                }
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

    @Override
    public List<Card> findCard(String nomeCarta) {

        List<Card> results = new ArrayList<>();

        for (CardCatalog catalog : cardCatalogs) {
            for(Card c : catalog.getCards()) {
                if(c.getNome().toLowerCase().contains(nomeCarta.toLowerCase())) {
                    results.add(c);

                    break;  //ogni venditore possiede SOLO 1 copia di questa carta
                }
            }
        }
        return results;
    }


    //helper per verificare se un selle possiede già una copia di una carta specifica
    @Override
    public boolean findCardBySeller(String nomeCarta, String seller){

        for(CardCatalog catalog : cardCatalogs) {
            if(catalog.getSeller().getSellerName().equals(seller)) {
                for(Card c : catalog.getCards()) {
                    if(c.getNome().toLowerCase().contains(nomeCarta.toLowerCase())) {
                        return true;        //ho trovato che il seller possiede già questa carta
                    }
                }
            }
        }
        return false;
    }
}
