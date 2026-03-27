package com.example.progettoispw.dao.cardcatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;

import java.util.List;

public interface CardCatalogDAO {

    List<CardCatalog> getAllCatalogs();     //restituisce tutti i cataloghi salvati

    void addCatalog(CardCatalog catalog);   //aggiunge un catalogo passato come parametro

    void removeCard(Card card, String sellerName);      //rimuove la carta passata come parametro, associata a uno specifico venditore

    void addCard(Card card, String sellerName);       //aggiunge al catalogo del sellerName la carta Card

    void updatePrice(String nomeCarta, String username, Float newPrice);    //aggiorna con newPrice il prezzo della carta nomeCarta di un seller username

     CardCatalog getCatalogBySeller(Seller seller);     //restituisce il catalogo associato al seller

     List<Card> findCard(String nomeCarta );        //trova la carta nomeCarta

     boolean findCardBySeller(String nomeCarta, String seller);     //trova la carta nomeCarta associata al seller, se esiste
}

