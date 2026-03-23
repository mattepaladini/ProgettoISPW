package testing;


import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.exception.DatabaseOperationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class DBConnectionTest {

    @Test
    @DisplayName("T01 - Singleton Test: getInstance deve restituire sempre lo stesso oggetto")
    void testGetInstance() {
        // Arrange & Act
        DBConnection firstInstance = DBConnection.getInstance();
        DBConnection secondInstance = DBConnection.getInstance();

        // Assert
        assertNotNull(firstInstance, "L'istanza Singleton non deve essere null");
        assertSame(firstInstance, secondInstance, "Le due istanze devono essere esattamente lo stesso oggetto in memoria");
    }

    @Test
    @DisplayName("T02 - Connection Test: getConnection deve restituire una connessione valida")
    void testGetConnection() throws DatabaseOperationException, SQLException {
        // Arrange
        DBConnection dbConnection = DBConnection.getInstance();

        // Act
        Connection connection = dbConnection.getConnection();

        // Assert
        assertNotNull(connection, "La connessione al DB non deve essere null");
        assertFalse(connection.isClosed(), "La connessione al DB dovrebbe risultare aperta");
    }

    @Test
    @DisplayName("T03 - Reconnection Test: se chiusa, getConnection deve ricrearne una nuova")
    void testReconnection() throws DatabaseOperationException, SQLException {
        // Arrange
        DBConnection dbConnection = DBConnection.getInstance();

        // Act 1: Otteniamo la prima connessione e verifichiamo che sia aperta
        Connection firstConnection = dbConnection.getConnection();
        assertFalse(firstConnection.isClosed(), "La prima connessione deve essere aperta inizialmente");

        // Act 2: Simuliamo la caduta del Database o il timeout chiudendola a mano
        firstConnection.close();
        assertTrue(firstConnection.isClosed(), "La prima connessione dovrebbe essere chiusa ora");

        // Act 3: Richiediamo la connessione (grazie al nostro 'conn.isClosed()' ne creerà una nuova)
        Connection secondConnection = dbConnection.getConnection();

        // Assert
        assertNotNull(secondConnection, "La seconda connessione non deve essere null");
        assertFalse(secondConnection.isClosed(), "La seconda connessione deve essere aperta e pronta all'uso");
        assertNotSame(firstConnection, secondConnection, "La nuova connessione deve essere un oggetto fisico diverso da quella vecchia");
    }
}

