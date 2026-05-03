package com.example.progettoispw.dao.follower;

import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.database.QueryManager;
import com.example.progettoispw.exception.DatabaseOperationException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FollowerDAODB extends FollowerDAODemo implements FollowerDAO {


    @Override
    public void follow(String buyer, String seller) {
        if(isFollowing(buyer, seller)) return;
        String query = QueryManager.getQuery("follower.follow");
        Connection conn = DBConnection.getInstance().getConnection();

        try(PreparedStatement ps = conn.prepareStatement(query)){
            ps.setString(1, seller);
            ps.setString(2, buyer);
            ps.executeUpdate();

            super.follow(buyer, seller);

        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore durante iscrizione"+e.getMessage());
        }

    }

    @Override
    public void unfollow(String buyerUsername, String sellerUsername) {
        String query = QueryManager.getQuery("follower.unfollow");
        Connection conn = DBConnection.getInstance().getConnection();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, buyerUsername);
            stmt.setString(2, sellerUsername);
            stmt.executeUpdate();
            super.unfollow(buyerUsername, sellerUsername); // sincronizza la cache in RAM
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nella cancellazione iscrizione: " + e.getMessage());
        }
    }

    @Override
    public List<String> getFollowers(String sellerUsername) {
        String query = QueryManager.getQuery("follower.getFollowers");
        Connection conn = DBConnection.getInstance().getConnection();
        List<String> followers = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sellerUsername);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    followers.add(rs.getString("username_compratore"));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseOperationException("Errore nel recupero dei follower: " + e.getMessage());
        }

        return followers;
    }


    @Override
    public boolean isFollowing(String buyer, String seller){

        String query = QueryManager.getQuery("follower.isFollowing");
        Connection conn = DBConnection.getInstance().getConnection();

        try(PreparedStatement ps = conn.prepareStatement(query)){

            ps.setString(1, buyer);
            ps.setString(2, seller);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        }catch (SQLException e){
            throw new DatabaseOperationException("Errore nella verifica dell'iscrizione"+e.getMessage());
        }
    }

}
