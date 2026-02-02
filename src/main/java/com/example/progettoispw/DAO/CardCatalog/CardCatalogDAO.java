package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;

import java.util.List;

public interface CardCatalogDAO {

    List<CardCatalog> getAllCatalogs();

    void addCatalog(CardCatalog catalog);

    void removeCard(Card card, User sellerName);

    void addCard(Card card, User sellerName);

    void updatePrice(Card card);

    CardCatalog getSeller(String username);

     CardCatalog getCatalogBySeller(Seller seller);
}

//PER INSERIRE E AGGIORNARE PASSA BEAN
//PER RIMUOVERE E CERCARE USA PARAMETRI
