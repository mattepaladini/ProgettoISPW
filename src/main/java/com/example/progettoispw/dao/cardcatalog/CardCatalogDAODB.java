package com.example.progettoispw.dao.cardcatalog;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.database.QueryManager;
import com.example.progettoispw.exception.DatabaseExceptionMessages;
import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.model.*;
import com.mysql.cj.jdbc.CallableStatement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardCatalogDAODB extends CardCatalogDAODemo implements CardCatalogDAO {

    public static final String ERRORE_CANCELLAZIONE_CARTA_NEL_DB = "Errore cancellazione carta nel DB";
    private boolean isLoaded = false;

    private static final Logger log = Logger.getLogger(CardCatalogDAODB.class.getName());

    @Override
    public List<CardCatalog> getAllCatalogs() {
        loadCatalogs();
        return super.getAllCatalogs();
    }

    @Override
    public void addCatalog(CardCatalog catalog) {
        loadCatalogs();

        //salvo sul db
        String query = QueryManager.getQuery("cardcatalog.addCatalog");

        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            // Sostituiamo il '?' con il nome del venditore preso dall'oggetto Java
            stmt.setString(1, catalog.getSeller().getSellerName());

            // Eseguiamo l'inserimento fisico nel database
            stmt.execute();
            log.log(Level.INFO,"DEBUG: Creato nuovo catalogo nel DB per: {0}" , catalog.getSeller().getSellerName());
            System.out.println();

        } catch (SQLException e) {
           throw  new DatabaseOperationException(DatabaseExceptionMessages.CATA_CREATE_ERROR);
        }

        //

        super.addCatalog(catalog);

    }

    @Override
    public void removeCard(Card card, String sellerName) {
        loadCatalogs();

        String query = QueryManager.getQuery("cardcatalog.remove");
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, card.getNome());
            stmt.setString(2, sellerName);

            int deleted = stmt.executeUpdate();
            if(deleted <=0){
                throw new DatabaseOperationException(DatabaseExceptionMessages.REMOVE_CARD_ERROR);
            }

        } catch (SQLException e) {
            throw new DatabaseOperationException(DatabaseExceptionMessages.REMOVE_CARD_ERROR);
        }

        super.removeCard(card, sellerName);
    }

    @Override
    public void addCard(Card card, String sellerName) {

        loadCatalogs();

        String query = QueryManager.getQuery("cardcatalog.add");

        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt =  conn.prepareStatement(query)) {

            stmt.setString(1, card.getNome());
            stmt.setString(2, sellerName);
            stmt.setFloat(3, card.getPrezzoAttuale());
            stmt.setInt(4, card.getLivello());
            stmt.setString(5, card.getGradazione().name());
            stmt.setString(6, card.getAttributo().name());
            stmt.setString(7, card.getTipo().name());

            stmt.executeUpdate();

        } catch (SQLException e){
            throw new DatabaseOperationException(DatabaseExceptionMessages.ADD_CARD_ERROR);
        }

        super.addCard(card, sellerName);

    }

    @Override
    public void updatePrice(String nomeCarta, String username, Float newPrice) {

        loadCatalogs();

        String query = "{CALL UpdatePrice(?,?,?)}";
        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            stmt.setString(1, nomeCarta);
            stmt.setFloat(2, newPrice);
            stmt.setString(3, username);
            stmt.execute();

        } catch (SQLException e) {
            throw new DatabaseOperationException(DatabaseExceptionMessages.UPDATE_CARD_ERROR);
        }

        super.updatePrice(nomeCarta, username, newPrice);
    }

    @Override
    public CardCatalog getCatalogBySeller(Seller seller) {
        loadCatalogs();

        return super.getCatalogBySeller(seller);
    }

    @Override
    public List<Card> findCard(String nomeCarta){

        //non usiamo loadCatalogs() per evitare di caricare in ram delle righe del DB che andremo a scartare subito dopo

        List<Card> risultati = new ArrayList<>();

        String query = QueryManager.getQuery("cardcatalog.findCard");

        Connection conn = DBConnection.getInstance().getConnection();

        try(PreparedStatement stmt = conn.prepareStatement(query)
        ) {

            stmt.setString(1, nomeCarta);

            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){

                    CollectableCardBean cardBean = new CollectableCardBean();

                    cardBean.setNomeCarta(rs.getString("nome"));
                    cardBean.setPrezzoCorrente(rs.getFloat("prezzo"));
                    cardBean.setGradazione(Gradazione.valueOf(rs.getString("gradazione")));
                    cardBean.setVenditore(rs.getString("venditore_username"));

                    cardBean.setLivello(rs.getInt("livello"));
                    cardBean.setTipo(Type.valueOf(rs.getString("tipo")));
                    cardBean.setAttributo(Attribute.valueOf(rs.getString("attributo")));

                    Card card = new Card(cardBean.getNomeCarta(), cardBean.getPrezzoCorrente(), cardBean.getGradazione(), cardBean.getVenditore(), cardBean.getLivello(), cardBean.getAttributo(),cardBean.getTipo());

                    risultati.add(card);
                }

            }

        } catch (SQLException e) {
            throw new DatabaseOperationException(DatabaseExceptionMessages.FINDA_CARD_ERROR);
        }

        return risultati;
    }

    @Override
    public boolean findCardBySeller(String nomeCarta, String seller){
        loadCatalogs();
        return super.findCardBySeller(nomeCarta, seller);
    }


    public void loadCatalogs() {

        if(isLoaded)
            return;

        String query = "{CALL GetCatalogs()}";

        // Mappa temporanea per "raggruppare" le carte sotto lo stesso venditore
        // Chiave: nome del venditore | Valore: l'oggetto CardCatalog corrispondente
        HashMap<String, CardCatalog> catalogMap = new HashMap<>();
        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            if(stmt.execute()){
                processResultSet(stmt.getResultSet(), catalogMap);
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore caricamento dati "+e.getMessage());
        }

        for (CardCatalog catalog : catalogMap.values()) {
            super.addCatalog(catalog);
        }

        isLoaded = true;

    }

    // Metodo helper 1: Gestisce scorrimento del ResultSet e raggruppamento
    private void processResultSet(ResultSet rs, HashMap<String, CardCatalog> catalogMap) throws SQLException {
        while (rs.next()) {
            String sellerName = rs.getString("sellerName");

            CardCatalog catalog = catalogMap.computeIfAbsent(sellerName, k -> {
                Seller seller = new Seller(k, null);
                return new CardCatalog(seller);
            });

            String nomeCarta = rs.getString("nome");

            if (!rs.wasNull()) {
                // Estraiamo la creazione della carta in un altro metodo
                Card carta = buildCardFromResultSet(rs, nomeCarta, sellerName);
                catalog.addCollectableCard(carta);
            }
        }
    }

    // Metodo helper, si occupa solo di istanziare l'oggetto Card
    private Card buildCardFromResultSet(ResultSet rs, String nomeCarta, String sellerName) throws SQLException {
        Float prezzo = Float.parseFloat(rs.getString("prezzo"));
        int livello = Integer.parseInt(rs.getString("livello"));
        String tipo = rs.getString("tipo");
        String gradazione = rs.getString("gradazione");
        String attributo = rs.getString("attributo");

        Gradazione gradazioneEnum = Gradazione.valueOf(gradazione);
        Type tipoEnum = Type.valueOf(tipo);
        Attribute attributoEnum = Attribute.valueOf(attributo);

        return new Card(nomeCarta, prezzo, gradazioneEnum, sellerName, livello, attributoEnum, tipoEnum);
    }


}
