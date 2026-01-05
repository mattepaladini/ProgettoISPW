package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import model.CardCatalog;
import model.Seller;

import java.util.List;

public interface CardCatalogDAO {

    List<CardCatalog> getAllCatalogs();

    void addCatalog(CardCatalog catalog);

    void removeCard(CollectableCardBean card, String sellerName);

    void addCard(CollectableCardBean card, Seller sellerName);

    CardCatalog getSeller(String username);
}

//PER INSERIRE E AGGIORNARE PASSA BEAN
//PER RIMUOVERE E CERCARE USA PARAMETRI
