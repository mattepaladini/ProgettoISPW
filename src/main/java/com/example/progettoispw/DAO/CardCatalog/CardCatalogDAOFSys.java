package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.CardCatalog;

import com.example.progettoispw.model.Seller;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardCatalogDAOFSys implements CardCatalogDAO {

    private static  List<CardCatalog> memoryCatalogs = null;    //variabile di classe usata per CACHING ---> prima controllo se ho già tirato su dalla memoria poi faccio operazioni

    private static final String CATALOG_FILE = "catalogs.dat";

    public CardCatalogDAOFSys() {

    }

    @Override
    public List<CardCatalog> getAllCatalogs() {
        if(memoryCatalogs == null) {
            memoryCatalogs = loadCatalogs();
        }
        return memoryCatalogs;
    }

    @Override
    public void addCatalog(CardCatalog catalog) {

        List<CardCatalog> currentCatalogs = getAllCatalogs();

        currentCatalogs.add(catalog);
        saveData();

    }

    @Override
    public void removeCard(Card card, String sellerName) {

        List<CardCatalog> currentCatalogs = getAllCatalogs();

        boolean found = false;

        for(CardCatalog catalog : currentCatalogs) {
            String sellerSearched = catalog.getSeller().getSellerName();

            if(sellerName.equals(sellerSearched)) {
                catalog.getCards().remove(card);
                found = true;
            }
        }

        if(!found) {
            //TODO logger
        }
        saveData();

    }

    @Override
    public void addCard(Card card, Seller currentSeller) {

        List<CardCatalog> currentCatalogs = getAllCatalogs();

        boolean found = false;
        for (CardCatalog catalog : currentCatalogs) {
            Seller savedSeller = catalog.getSeller();

            if(savedSeller.getSellerName().equals(currentSeller.getSellerName())) {
                catalog.getCards().add(card);
                found = true;
            }
        }

        //SE NON ESISTE UN CATALOGO ASSOCIATO A QUESTO VENDITORE LO CREO
        if(!found) {
            CardCatalog emptyCatalog = new CardCatalog(currentSeller);
            emptyCatalog.getCards().add(card);
            currentCatalogs.add(emptyCatalog);
        }

        saveData();
       // memoryCatalogs.add();

    }

    @Override
    public CardCatalog getSeller(String username) {
        return null;
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {
        return null;
    }

    @SuppressWarnings("unchecked")
    private List<CardCatalog> loadCatalogs() {
        File file = new File(CATALOG_FILE);

        if(!file.exists()){
            return new ArrayList<>();
        }

        try(FileInputStream fis = new FileInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(fis)) {

            return (List<CardCatalog>) ois.readObject();

        } catch (ClassNotFoundException|FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e ) {
            throw new RuntimeException(e);
        }
    }

    private void saveData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CATALOG_FILE))) {
            oos.writeObject(memoryCatalogs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
