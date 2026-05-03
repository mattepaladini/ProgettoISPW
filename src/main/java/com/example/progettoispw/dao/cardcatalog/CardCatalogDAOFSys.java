package com.example.progettoispw.dao.cardcatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.exception.FSysOperationException;
import com.example.progettoispw.model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardCatalogDAOFSys extends CardCatalogDAODemo implements CardCatalogDAO {

    private static final String FOLDER_NAME = "persistence";
    private static final String CATALOG_FILE = "catalogs.txt";
    private static final String SEPARATOR = ",";

    private boolean isLoaded = false;

    private final Logger logger = Logger.getLogger(CardCatalogDAOFSys.class.getName());

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
        logger.log(Level.INFO, "Catalogo aggiunto con successo.");

    }

    //
    @Override
    public void removeCard(Card card, String sellerName) {

        loadCatalogs();
        super.removeCard(card, sellerName);
        updateSingleLineCard();
    }

    @Override
    public void addCard(Card card, String currentSeller) {

        loadCatalogs();

        Seller seller = new Seller(currentSeller, null);
        addCardToCatalogHelper(currentSeller, seller, card);
        super.addCard(card, currentSeller);

        appendNewCardToFile(card, currentSeller);

        logger.log(Level.INFO, "Carta aggiunta con successo.");

    }

    @Override
    public void updatePrice(String nomeCarta, String username, Float newPrice) {
            loadCatalogs();
            super.updatePrice(nomeCarta, username, newPrice);

            updateSingleLineCard();

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

                    String nome = parts[0];
                    if(parts.length >= 7 && nome.equalsIgnoreCase(nomeCarta)){       //controllo subito se il nome coincide
                        CollectableCardBean cardBean = new CollectableCardBean();

                        cardBean.setName(nome);
                        cardBean.setPrice(Float.parseFloat(parts[1]));
                        cardBean.setGradation(Gradation.valueOf(parts[2]));
                        cardBean.setSeller(parts[3]);
                        cardBean.setLevel(Integer.parseInt(parts[4]));
                        cardBean.setAttribute(Attribute.valueOf(parts[5]));
                        cardBean.setType(Type.valueOf(parts[6]));

                        Card card = new Card(cardBean.getName(), cardBean.getPrice(), cardBean.getGradation(), cardBean.getSeller(), cardBean.getLevel(), cardBean.getAttribute(), cardBean.getType());

                        resultCards.add(card);
                    }
                }
            }catch (IOException e){
               throw new FSysOperationException(e.getMessage());
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

        //se è già caricato esco
        if (isLoaded) {
            return;
        }

        File file = getStorageFile();

        // 2. EARLY RETURN: Se il file non esiste, segniamo come caricato (vuoto) e usciamo
        if (!file.exists()) {
            isLoaded = true;
            return;
        }

        // 3. Lettura pulita senza annidamenti mostruosi
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line); //
            }
        } catch (Exception e) {
            throw  new FSysOperationException(e.getMessage());
        }

        isLoaded = true;

    }

    private void processLine(String line) {
        if (line.trim().isEmpty()) return;

        String[] parts = line.split(SEPARATOR);
        if (parts.length < 7) return;

        String nome = parts[0];
        float prezzo = Float.parseFloat(parts[1]);
        String gradStr = parts[2].toUpperCase();
        String ownerUsername = parts[3];
        int livello = Integer.parseInt(parts[4]);
        String attrStr = parts[5].toUpperCase();
        String typeStr = parts[6].toUpperCase();

        Seller owner = new Seller(ownerUsername, null);
        Card card = new Card(
                nome, prezzo, Gradation.valueOf(gradStr), ownerUsername,
                livello, Attribute.valueOf(attrStr), Type.valueOf(typeStr)
        );

        // Deleghiamo la ricerca e l'inserimento
        addCardToCatalogHelper(ownerUsername, owner, card);
    }

    //HELPER
    private void addCardToCatalogHelper(String ownerUsername, Seller owner, Card card) {
        CardCatalog targetCatalog = null;

        // Cerchiamo se esiste già
        for (CardCatalog c : super.getAllCatalogs()) {
            if (c.getSeller().getUsername().equals(ownerUsername)) {
                targetCatalog = c;
                break;
            }
        }

        // Se non esiste, lo creiamo e lo aggiungiamo ALLA LISTA UNA SOLA VOLTA
        if (targetCatalog == null) {
            targetCatalog = new CardCatalog(owner);
            super.addCatalog(targetCatalog);
        }

        // Aggiungiamo la carta al catalogo (che sia nuovo o vecchio)
        targetCatalog.addCollectableCard(card);
    }

    // Metodo helper per convertire l'oggetto Card in stringa CSV
    private String convertCardToString(Card card, String owner) {
        StringBuilder sb = new StringBuilder();
        sb.append(card.getName()).append(SEPARATOR);
        sb.append(card.getPrice()).append(SEPARATOR);
        sb.append(card.getGradation()).append(SEPARATOR);
        sb.append(owner).append(SEPARATOR);
        sb.append(card.getLevel()).append(SEPARATOR);
        sb.append(card.getAttribute()).append(SEPARATOR);
        sb.append(card.getType()).append(SEPARATOR);

        return sb.toString();
    }


    //Helper uasto solo per aggiungere una nuova riga sul file (ADD CARD)
    private void appendNewCardToFile(Card card, String user) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getStorageFile(), true))) {

            bw.write(convertCardToString(card, user));
            bw.newLine();
        } catch (IOException e) {
            throw new FSysOperationException(e.getMessage());
        }
    }

    //Helper usato solo per sovrascrivere una riga sul file (UPDATE PRICE o REMOVE CARD)
    private void updateSingleLineCard() {
        File file = getStorageFile();

        // Usiamo un BufferedWriter che sovrascrive il file esistente (senza l'append=true)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {

            // Scorriamo tutti i cataloghi e tutte le carte in memoria
            for (CardCatalog catalog : super.getAllCatalogs()) {
                String ownerUsername = catalog.getSeller().getUsername();

                for (Card c : catalog.getCards()) {
                    // Ricostruiamo la riga col formato esatto: Nome;Prezzo;Gradation;OWNER;Livello;Attributo;Tipo
                    String line = c.getName() + SEPARATOR
                            + c.getPrice() + SEPARATOR // Questo sarà il prezzo NUOVO aggiornato al passo 2!
                            + c.getGradation().name() + SEPARATOR
                            + ownerUsername + SEPARATOR
                            + c.getLevel() + SEPARATOR
                            + c.getAttribute().name() + SEPARATOR
                            + c.getType().name();

                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            throw  new FSysOperationException(e.getMessage());
        }
    }
}

