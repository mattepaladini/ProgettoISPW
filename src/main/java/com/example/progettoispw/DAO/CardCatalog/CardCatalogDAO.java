package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.List;

public interface CardCatalogDAO {

    List<CardCatalog> getAllCatalogs();

    void addCatalog(CardCatalog catalog);

    void removeCard(Card card, String sellerName);

    void addCard(Card card, Seller sellerName);

    CardCatalog getSeller(String username);
}

//PER INSERIRE E AGGIORNARE PASSA BEAN
//PER RIMUOVERE E CERCARE USA PARAMETRI
