package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardCatalogDAOFSys extends CardCatalogDAODemo implements CardCatalogDAO {

    private static  List<CardCatalog> memoryCatalogs = new ArrayList<>();    //variabile di classe usata per CACHING ---> prima controllo se ho già tirato su dalla memoria poi faccio operazioni

    private static final String FOLDER_NAME = "persistence";
    private static final String CATALOG_FILE = "catalogs.txt";
    private static final String SEPARATOR = ",";

    private boolean isLoaded = false;

    private final Logger logger = Logger.getLogger(CardCatalogDAOFSys.class.getName());

    public CardCatalogDAOFSys() {

    }

    //
    @Override
    public List<CardCatalog> getAllCatalogs() {
        loadCatalogs();
        return super.getAllCatalogs();
    }

    //
    @Override
    public void addCatalog(CardCatalog catalog) {

        loadCatalogs();
        super.addCatalog(catalog);

    }

    //
    @Override
    public void removeCard(Card card, User sellerName) {

        loadCatalogs();

        super.removeCard(card, sellerName);
        saveMemoryCatalogsToFile(card, sellerName.getUsername());

    }

    @Override
    public void addCard(Card card, String currentSeller) {

        loadCatalogs();

        super.addCard(card, currentSeller);

        saveMemoryCatalogsToFile(card, currentSeller);

        //TODO controlla che la carta con quel nome non esista già

    }

    @Override
    public void updatePrice(String nomeCarta, String username, Float newPrice) {

    }

    //TODO da togliere perchè tanto c'è il SessionManager
    @Override
    public CardCatalog getSeller(String username) {
        return null;
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {

        loadCatalogs();

        return super.getCatalogBySeller(seller);
    }

    @Override
    public List<Card> findCard(String nomeCarta){

        //non carico tutte le informazioni direttamente

        List<Card> resultCards = new ArrayList<>();

        File file = getStorageFile();
        if(file.exists()){

            try (BufferedReader br = new BufferedReader(new FileReader(file))) {

                String line;
                while ((line = br.readLine()) != null) {
                    if (line.trim().isEmpty()) continue;

                    String[] parts = line.split(SEPARATOR);
                    if (parts.length < 7) continue;

                    String nome = parts[0];
                    if(nome.equalsIgnoreCase(nomeCarta)){       //controllo subito se il nome coincide
                        CollectableCardBean cardBean = new CollectableCardBean();

                        cardBean.setNomeCarta(nome);
                        cardBean.setPrezzoCorrente(Float.parseFloat(parts[1]));
                        cardBean.setGradazione(Gradazione.valueOf(parts[2]));
                        cardBean.setVenditore(parts[3]);
                        cardBean.setLivello(Integer.parseInt(parts[4]));
                        cardBean.setAttributo(Attribute.valueOf(parts[5]));
                        cardBean.setTipo(Type.valueOf(parts[6]));

                        Card card = new Card(cardBean.getNomeCarta(), cardBean.getPrezzoCorrente(), cardBean.getGradazione(), cardBean.getVenditore(), cardBean.getLivello(), cardBean.getAttributo(), cardBean.getTipo());

                        resultCards.add(card);
                    }
                }
            }catch (IOException e){
                throw new RuntimeException(e.getMessage());
            }
        }
        return resultCards;
    }

    @Override
    public boolean findCardBySeller(String nomeCarta, String seller){
        loadCatalogs();
        return super.findCardBySeller(nomeCarta, seller);
    }

    private File getStorageFile()  {
        File folder = new File(FOLDER_NAME);
        if (!folder.exists()) folder.mkdir();
        return new File(folder, CATALOG_FILE);
    }

    //METODO PER CARICARE I CATALOGHI LETTI DA FILE
    private void loadCatalogs() {

        if(!isLoaded){

            File file = getStorageFile();
            if(file.exists()){

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
                                nome, prezzo, Gradazione.valueOf(gradStr), owner.getUsername(),
                                livello, Attribute.valueOf(attrStr), Type.valueOf(typeStr)
                        );


                        // LOGICA DI RAGGRUPPAMENTO
                        // Cerco se ho già creato un catalogo per questo utente nella lista 'catalogs'
                        CardCatalog existingCatalog = null;
                        for (CardCatalog c : memoryCatalogs) {
                            if (c.getSeller().getUsername().equals(ownerUsername)) {
                                existingCatalog = c;
                                break;
                            }
                        }

                        // Se non esiste, lo creo e lo aggiungo alla lista
                        if (existingCatalog == null) {
                            logger.log(Level.INFO, "DEBUG, catalogo non esistente per "+ownerUsername);
                            existingCatalog = new CardCatalog(owner);
                            memoryCatalogs.add(existingCatalog);
                        }

                        // Aggiungo la carta al catalogo trovato/creato
                        existingCatalog.addCollectableCard(card);

                        memoryCatalogs.add(existingCatalog);

                    }
                }catch (Exception e) {
                    logger.log(Level.SEVERE, "Errore nel caricamento dei cataloghi");
                    throw new RuntimeException(e.getMessage());
                }

            }
        }

        isLoaded=true;
    }

    // Metodo helper per convertire l'oggetto Card in stringa CSV
    private String convertCardToString(Card card, String owner) {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getNome()).append(SEPARATOR);
        sb.append(card.getPrezzoAttuale()).append(SEPARATOR);
        sb.append(card.getGradazione()).append(SEPARATOR);
        sb.append(owner).append(SEPARATOR);
        sb.append(card.getLivello()).append(SEPARATOR);
        sb.append(card.getAttributo()).append(SEPARATOR);
        sb.append(card.getTipo()).append(SEPARATOR);

        return sb.toString();
    }


    private void saveMemoryCatalogsToFile(Card card, String user) {
        if (memoryCatalogs == null) return;
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getStorageFile(), true))) {

            bw.write(convertCardToString(card, user));
            bw.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
