package com.example.progettoispw.dao.cardcatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardCatalogDAODemo implements CardCatalogDAO {

    protected List<CardCatalog> cardCatalogs = new ArrayList<>();

    private static final Logger log = Logger.getLogger(CardCatalogDAODemo.class.getName());

    @Override
    public List<CardCatalog> getAllCatalogs() {
        return this.cardCatalogs;
    }

    @Override
    public void addCatalog(CardCatalog catalog) {
        this.cardCatalogs.add(catalog);

    }

    @Override
    public void removeCard(Card card, String sellerName) {

            for(CardCatalog catalog : getAllCatalogs()) {
                if(catalog.getSeller().getSellerName().equals(sellerName)){
                    catalog.getCards().removeIf(c -> c.getNome().equals(card.getNome()));
                    break;
                }

            }
            log.log(Level.INFO, "Carta " +card.getNome() + " rimossa con successo");
    }

    @Override
    public void addCard(Card card, String sellerName) {

            for(CardCatalog catalog : getAllCatalogs()) {
                if(catalog.getSeller().getSellerName().equals(sellerName)) {
                    catalog.addCollectableCard(card);
                    log.log(Level.INFO, "Carta aggiunta al catalogo di: {0}",catalog.getSeller().getSellerName());
                }
            }

    }

    @Override
    public void updatePrice(String nomeCarta, String username, Float newPrice) {
        for(CardCatalog catalog : getAllCatalogs()) {
            if(catalog.getSeller().getSellerName().equals(username)) {
                for(Card c : catalog.getCards()){
                    if(c.getNome().equals(nomeCarta)){
                        c.setPrezzoAttuale(newPrice);
                    }
                }
            }

        }
        log.log(Level.INFO, "Prezzo modificato con successo");
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {

        for(CardCatalog catalogs : getAllCatalogs()) {
            if(catalogs.getSeller().getSellerName().equals(seller.getSellerName())) {
                return catalogs;
            }
        }

        return null;
    }

    @Override
    public List<Card> findCard(String nomeCarta) {

        List<Card> results = new ArrayList<>();

        for (CardCatalog catalog : getAllCatalogs()) {
            for(Card c : catalog.getCards()) {
                if(c.getNome().toLowerCase().contains(nomeCarta.toLowerCase())) {
                    results.add(c);
                    log.log(Level.INFO, "Carta trovata.");
                    break;  //ogni venditore possiede SOLO 1 copia di questa carta
                }
            }
        }
        return results;
    }


    //helper per verificare se un selle possiede già una copia di una carta specifica
    @Override
    public boolean findCardBySeller(String nomeCarta, String seller){

        for(CardCatalog catalog : getAllCatalogs()) {
            if(catalog.getSeller().getSellerName().equals(seller)) {
                for(Card c : catalog.getCards()) {
                    if(c.getNome().toLowerCase().contains(nomeCarta.toLowerCase())) {
                        log.log(Level.INFO, "Carta trovata in un catalogo.");
                        return true;        //ho trovato che il seller possiede già questa carta
                    }
                }
            }
        }
        return false;
    }
}
