package com.example.progettoispw.dao.cardcatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.List;

public interface CardCatalogDAO {

    List<CardCatalog> getAllCatalogs();

    void addCatalog(CardCatalog catalog);

    void removeCard(Card card, String sellerName);

    void addCard(Card card, String sellerName);

    void updatePrice(String nomeCarta, String username, Float newPrice);

     CardCatalog getCatalogBySeller(Seller seller);

     List<Card> findCard(String nomeCarta );

     boolean findCardBySeller(String nomeCarta, String seller);
}

//PER INSERIRE E AGGIORNARE PASSA BEAN
//PER RIMUOVERE E CERCARE USA PARAMETRI
