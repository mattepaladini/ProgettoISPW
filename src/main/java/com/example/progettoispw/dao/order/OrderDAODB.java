package com.example.progettoispw.dao.order;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.exception.databaseoperationException;
import com.example.progettoispw.model.*;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDAODB extends OrderDAODemo implements OrderDAO {

    private boolean isLoaded = false;


    @Override
    public List<Order> getOrdersByUser(User user) {
        loadOrders();
        return super.getOrdersByUser(user);
    }

    @Override
    public List<Order> getOrdersByID(int id) {
        loadOrders();
        return super.getOrdersByID(id);
    }

    @Override
    public void saveOrder(Order order) {

        super.saveOrder(order);

        Connection conn = DBConnection.getInstance().getConnection();
        String querySaveOrder = "{CALL SaveOrder(?, ?, ?, ?, ?)}";

        try(
            CallableStatement cstmt = conn.prepareCall(querySaveOrder)) {

            cstmt.setInt(1, order.getId());
            cstmt.setString(2, order.getCompratore());
            cstmt.setString(3, order.getDataOrdine());
            cstmt.setString(4, order.getIndirizzoSpedizione());
            cstmt.setFloat(5, order.getTotale());

            cstmt.execute();

            saveOrderCard(order);

        } catch (SQLException e) {
            throw new databaseoperationException(e.getMessage());
        }

    }

    public void saveOrderCard(Order order){
        String querySaveCards = "{CALL SaveOrderCard(?, ?, ?)}";
        List<Card> shoppedCards = order.getCarteOrdinate();
        Connection conn = DBConnection.getInstance().getConnection();
        try(
                CallableStatement cstmt2 = conn.prepareCall(querySaveCards)) {
            for(Card card : shoppedCards) {
                cstmt2.setInt(1, order.getId());
                cstmt2.setString(2, card.getNome());
                cstmt2.setString(3, card.getVenditore());

                // Accodiamo l'istruzione in memoria per ottimizzare la rete
                cstmt2.addBatch();
            }

            cstmt2.executeBatch();
        } catch (SQLException e) {
            throw new databaseoperationException(e.getMessage());
        }

    }

    public List<Card> loadCardsForOrder(int id) throws SQLException {

        List<Card> cardsOrder = new ArrayList<>();

        String query = "{CALL GetCardsForOrder(?)}";

        try(Connection conn = DBConnection.getInstance().getConnection();
            CallableStatement cstmt = conn.prepareCall(query)) {

            cstmt.setInt(1, id);
            try (ResultSet rs = cstmt.executeQuery()) {
                while(rs.next()) {

                    CollectableCardBean cardBean = new CollectableCardBean();

                    cardBean.setNomeCarta(rs.getString("nome"));
                    cardBean.setPrezzoCorrente(rs.getFloat("prezzo"));
                    cardBean.setGradazione(Gradazione.valueOf(rs.getString("gradazione")));
                    cardBean.setVenditore(rs.getString("venditore_username"));

                    cardBean.setLivello(rs.getInt("livello"));
                    cardBean.setTipo(Type.valueOf(rs.getString("tipo")));
                    cardBean.setAttributo(Attribute.valueOf(rs.getString("attributo")));

                    Card card = new Card(cardBean.getNomeCarta(), cardBean.getPrezzoCorrente(), cardBean.getGradazione(), cardBean.getVenditore(), cardBean.getLivello(), cardBean.getAttributo(),cardBean.getTipo());

                    cardsOrder.add(card);
                }
            }catch (SQLException e) {
                throw new databaseoperationException(e.getMessage());
            }

        }

        return cardsOrder;
    }


    public void loadOrders(){
        if(isLoaded){
            return;
        }

        String query = "{CALL GetOrders()}";

        List<Order> listaOrdini = new ArrayList<>();

        // 1. Usiamo il try-with-resources per chiudere in automatico la connessione,
        // lo statement e il resultset. SonarCloud adora questa sintassi!
        try (Connection conn = DBConnection.getInstance().getConnection(); // Sostituisci con il tuo gestore connessioni
             CallableStatement stmt = conn.prepareCall(query);
             ResultSet rs = stmt.executeQuery()) {

            // 2. Iteriamo su ogni riga restituita dalla procedura
            while (rs.next()) {
                Order ordine = new Order(rs.getInt("orderID"),
                        loadCardsForOrder(rs.getInt("id_ordine")),
                        rs.getString("shippingAddress"),
                        rs.getString("username_compratore"),
                        rs.getFloat("total"),
                        rs.getDate("orderDate").toString()
                        );

                listaOrdini.add(ordine);
                isLoaded=true;
            }

        } catch (SQLException e) {
            throw new databaseoperationException(e.getMessage());
        }
    }
}


