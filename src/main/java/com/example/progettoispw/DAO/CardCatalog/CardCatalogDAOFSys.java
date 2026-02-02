package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CardCatalogDAOFSys implements CardCatalogDAO {

    private static  List<CardCatalog> memoryCatalogs = new ArrayList<>();    //variabile di classe usata per CACHING ---> prima controllo se ho già tirato su dalla memoria poi faccio operazioni

    private static final String FOLDER_NAME = "persistence";
    private static final String CATALOG_FILE = "catalogs.txt";
    private static final String SEPARATOR = ",";

    public CardCatalogDAOFSys() {

    }

    //
    @Override
    public List<CardCatalog> getAllCatalogs() {
        if(memoryCatalogs == null) {
            memoryCatalogs = loadCatalogs();
        }
        return memoryCatalogs;
    }

    //
    @Override
    public void addCatalog(CardCatalog catalog) {



    }

    //
    @Override
    public void removeCard(Card card, User sellerName) {

        loadCatalogs();

        boolean removed = false;
        for (CardCatalog c : memoryCatalogs) {
            if (c.getSeller().getUsername().equals(sellerName)) {
                // Rimuovo usando l'ID che è univoco

                removed = c.getCards().removeIf(item ->
                        item.getNome().equals(card.getNome()));
                break;
            }
        }

        if (removed) {
            saveMemoryCatalogsToFile(card, sellerName); // Riscrivo tutto il file
        }

    }

    @Override
    public void addCard(Card card, User currentSeller) {

        loadCatalogs();

        System.out.println("Debug, pronto per aggiungere");
        // 2. Aggiorno la LISTA IN MEMORIA
        boolean catalogFound = false;
        for (CardCatalog c : memoryCatalogs) {
            if (c.getSeller().getUsername().equals(currentSeller
                    .getUsername())) {
                c.addCollectableCard(card);
                catalogFound = true;
                break;
            }
        }

        saveMemoryCatalogsToFile(card, currentSeller);

        //TODO controlla che la carta con quel nome non esista già

    }

    @Override
    public void updatePrice(Card card) {

    }

    //TODO da togliere perchè tanto c'è il SessionManager
    @Override
    public CardCatalog getSeller(String username) {
        return null;
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {

        List<CardCatalog> allCatalogs = loadCatalogs();

        for (CardCatalog catalog : allCatalogs) {
            if (catalog.getSeller().getUsername().equals(seller.getUsername())) {
                return catalog;
            }
        }

        // Se non esiste, ritorno un catalogo vuoto per evitare NullPointerException
        return new CardCatalog(seller);
    }

    private File getStorageFile()  {
        File folder = new File(FOLDER_NAME);
        if (!folder.exists()) folder.mkdir();
        return new File(folder, CATALOG_FILE);
    }

    //METODO PER CARICARE I CATALOGHI LETTI DA FILE
    private List<CardCatalog> loadCatalogs() {

        if (!memoryCatalogs.isEmpty()) {
            System.out.println("DEBUG: Uso memoryCatalogs (RAM). Nessuna lettura file.");
            return memoryCatalogs;
        }

        System.out.println("DEBUG, memcata vuoto");
        List<CardCatalog> catalogs = new ArrayList<>();
        File file = null;

        try {
            file = getStorageFile();
            if (file.length()==0) return memoryCatalogs; // Ritorna lista vuota se file non c'è

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    // Parsing della riga
                    // Formato: Nome;Prezzo;Gradazione;OWNER;Livello;Attributo;Tipo
                    String[] parts = line.split(SEPARATOR);
                    if (parts.length < 7) continue;

                    String nome = parts[0];
                    float prezzo = Float.parseFloat(parts[1]);
                    String gradStr = parts[2];
                    String ownerUsername = parts[3];
                    int livello = Integer.parseInt(parts[4]);
                    String attrStr = parts[5];
                    String typeStr = parts[6];

                    // Creo un Seller "placeholder" solo con lo username per l'associazione
                    Seller owner = new Seller(ownerUsername, null);

                    // Creo la carta
                    Card card = new Card(
                            nome, prezzo, Gradazione.valueOf(gradStr), owner,
                             livello, Attribute.valueOf(attrStr), Type.valueOf(typeStr)
                    );


                    // LOGICA DI RAGGRUPPAMENTO
                    // Cerco se ho già creato un catalogo per questo utente nella lista 'catalogs'
                    CardCatalog existingCatalog = null;
                    for (CardCatalog c : memoryCatalogs) {
                        if (c.getSeller().getUsername().equals(ownerUsername)) {
                            System.out.println("DEBUG, catalogo esistente per "+ownerUsername);
                            existingCatalog = c;
                            break;
                        }
                    }

                    // Se non esiste, lo creo e lo aggiungo alla lista
                    if (existingCatalog == null) {
                        System.out.println("DEBUG, catalogo non esistente per "+ownerUsername);
                        existingCatalog = new CardCatalog(owner);
                        memoryCatalogs.add(existingCatalog);
                    }

                    // Aggiungo la carta al catalogo trovato/creato
                    existingCatalog.addCollectableCard(card);

                    memoryCatalogs.add(existingCatalog);

                }
            }
        } catch (Exception e) {
            System.err.println("Errore durante il caricamento dei cataloghi: " + e.getMessage());
            e.printStackTrace();
        }

        return memoryCatalogs;
    }

    // Metodo helper per convertire l'oggetto Card in stringa CSV
    private String convertCardToString(Card card, User owner) {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getNome()).append(SEPARATOR);
        sb.append(card.getPrezzoAttuale()).append(SEPARATOR);
        sb.append(card.getGradazione()).append(SEPARATOR);
        sb.append(owner.getUsername()).append(SEPARATOR);
        sb.append(card.getLivello()).append(SEPARATOR);
        sb.append(card.getAttributo()).append(SEPARATOR);
        sb.append(card.getTipo()).append(SEPARATOR);

        return sb.toString();
    }


    private void saveMemoryCatalogsToFile(Card card, User user) {
        if (memoryCatalogs == null) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getStorageFile(), true))) {

            bw.write(convertCardToString(card, user));
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
//mdofica qui perchè forse riscrive tutto
            /*for (CardCatalog catalog : memoryCatalogs) {
                for (Card card : catalog.getCards()) { // Assumo getCardList() ritorni List<Card>
                    bw.write(convertCardToString(card, catalog.getSeller()));
                    bw.newLine();
                }
            }*/