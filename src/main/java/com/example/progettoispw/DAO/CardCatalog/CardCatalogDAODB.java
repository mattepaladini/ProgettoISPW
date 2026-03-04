package com.example.progettoispw.DAO.CardCatalog;

import com.example.progettoispw.DataBase.DBConnection;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.*;
import com.mysql.cj.jdbc.CallableStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CardCatalogDAODB extends CardCatalogDAODemo implements CardCatalogDAO {

    private boolean isLoaded = false;

    private Logger log = Logger.getLogger(this.getClass().getName());

    @Override
    public List<CardCatalog> getAllCatalogs() {
        loadCatalogs();
        return super.getAllCatalogs();
    }

    @Override
    public void addCatalog(CardCatalog catalog) {
        loadCatalogs();

        //salvo sul db

        String query = "INSERT INTO CardCatalog (sellerName) VALUES (?)";
        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            // Sostituiamo il '?' con il nome del venditore preso dall'oggetto Java
            stmt.setString(1, catalog.getSeller().getSellerName());

            // Eseguiamo l'inserimento fisico nel database
            stmt.execute();
            System.out.println("DEBUG: Creato nuovo catalogo nel DB per: " + catalog.getSeller().getSellerName());

        } catch (SQLException e) {
            log.log(Level.SEVERE, "Errore creazione catalogo nel DB");
        }

        //

        super.addCatalog(catalog);


    }

    @Override
    public void removeCard(Card card, User sellerName) {
        loadCatalogs();

        String query = "DELETE FROM Card WHERE nome = ? AND sellerName = ?";
        Connection conn = DBConnection.getInstance().getConnection();

        try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

            // Sostituiamo il '?' con il nome del venditore preso dall'oggetto Java
            stmt.setString(1, card.getNome());
            stmt.setString(2, sellerName.getUsername());

            int deleted = stmt.executeUpdate();
            if(deleted <=0){
                log.log(Level.SEVERE, "Errore cancellazione carta");
            }


        } catch (SQLException e) {
            log.log(Level.SEVERE, "Errore cancellazione carta nel DB");
        }


        super.removeCard(card, sellerName);
    }

    @Override
    public void addCard(Card card, String sellerName) {

        loadCatalogs();

        String query = "INSERT INTO Carta (nome, venditore_username, prezzo,livello, gradazione, attributo, tipo  ) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";
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
            System.out.println("DEBUG: Sto scrivendo fisicamente nel database chiamato: " + conn.getCatalog());

        } catch (SQLException e){
            log.log(Level.SEVERE, "Errore creazione carta");
            throw new RuntimeException("Inserimento fallito su DB", e);
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
            log.log(Level.INFO, "Prezzo aggiornato");

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        super.updatePrice(nomeCarta, username, newPrice);
    }

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

        //non usiamo loadCatalogs() per evitare di caricare in ram delle righe del DB che andremo a scartare subito dopo

        List<Card> risultati = new ArrayList<>();

        String query = "SELECT * FROM Carta WHERE nome LIKE ?";
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
            throw new RuntimeException(e);
        }

        return risultati;
    }

    @Override
    public boolean findCardBySeller(String nomeCarta, String seller){
        loadCatalogs();
        return super.findCardBySeller(nomeCarta, seller);
    }

    public void loadCatalogs() {

        String query = "{CALL GetCatalogs()}";

        if (!isLoaded) {

            // Mappa temporanea per "raggruppare" le carte sotto lo stesso venditore
            // Chiave: nome del venditore | Valore: l'oggetto CardCatalog corrispondente
            HashMap<String, CardCatalog> catalogMap = new HashMap<>();
            Connection conn = DBConnection.getInstance().getConnection();

            try (CallableStatement stmt = (CallableStatement) conn.prepareCall(query)) {

                if (stmt.execute()) {
                    try {
                        ResultSet rs = stmt.getResultSet();
                        while (rs.next()) {

                            String sellerName = rs.getString("sellerName");
                            User user = new User(sellerName);
                            Seller seller = new Seller(sellerName, null);

                            CardCatalog catalog  = catalogMap.get(sellerName);
                            if (catalog == null) {

                                catalog = new CardCatalog(seller);
                                catalogMap.put(sellerName, catalog);
                            }
                                String nomeCarta = rs.getString("nome");

                                if(!rs.wasNull()){
                                    Float prezzo = Float.parseFloat(rs.getString("prezzo"));
                                    int livello = Integer.parseInt(rs.getString("livello"));
                                    String tipo = rs.getString("tipo");
                                    String gradazione = rs.getString("gradazione");
                                    String attributo = rs.getString("attributo");

                                    Gradazione gradazioneEnum = Gradazione.valueOf(gradazione);
                                    Type tipoEnum = Type.valueOf(tipo);
                                    Attribute attributoEnum = Attribute.valueOf(attributo);

                                    Card carta = new Card(nomeCarta, prezzo, gradazioneEnum, user.getUsername() ,livello, attributoEnum, tipoEnum);
                                    catalog.getCards().add(carta);

                                }


                            for(CardCatalog catalogs : catalogMap.values()){
                                super.addCatalog(catalogs);
                            }
                        }
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        isLoaded=true;
    }


}
