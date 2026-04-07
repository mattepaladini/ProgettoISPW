package com.example.progettoispw.dao.notification;

import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.database.QueryManager;
import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.model.Notification;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAODB extends NotificationDAODemo implements NotificationDAO{

    @Override
    public void saveNotification(Notification notification) {

        Connection conn = DBConnection.getInstance().getConnection();
        String query = QueryManager.getQuery("notification.save");

        try(PreparedStatement ps = conn.prepareStatement(query, java.sql.Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, notification.getRecipient());
            ps.setString(2, notification.getSender());
            ps.setString(3, notification.getMessage());
            ps.setString(4, notification.getDate());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if(rs.next()) {
                    notification.setId(rs.getInt(1));
                }
            }

            super.saveNotification(notification);

        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore salvataggio notifica"+e.getMessage());
        }

    }

    @Override
    public List<Notification> getUnreadNotifications(String senderUsername) {
        return(fetchNotifications(QueryManager.getQuery("notification.getUnread"), senderUsername));
    }

    @Override
    public List<Notification> getAllNotifications(String senderUsername) {
        return fetchNotifications(QueryManager.getQuery("notification.getAll"), senderUsername);
    }

    @Override
    public void markAsRead(int notificationId) {

        Connection conn = DBConnection.getInstance().getConnection();
        String query = QueryManager.getQuery("notification.markAsRead");

        try(PreparedStatement stmt = conn.prepareStatement(query)){

            stmt.setInt(1, notificationId);
            stmt.executeUpdate();
            super.markAsRead(notificationId);

        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore segna-come-letto della notifica"+e.getMessage());
        }

    }

    //HELPER

    private List<Notification> fetchNotifications(String query, String recipient) {
        Connection conn = DBConnection.getInstance().getConnection();
        List<Notification> result = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, recipient);
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                result.add(buildFromResultSet(rs));
            }

        }catch (SQLException e) {
            throw new DatabaseOperationException("Errore salvataggio notifica"+e.getMessage());
        }
        return result;
    }

    private Notification buildFromResultSet(ResultSet rs) throws SQLException {
        return new Notification(
                rs.getInt("id"),
                rs.getString("destinatario"),
                rs.getString("mittente"),
                rs.getString("messaggio"),
                rs.getString("data"),
                rs.getBoolean("letta")
        );
    }
}
