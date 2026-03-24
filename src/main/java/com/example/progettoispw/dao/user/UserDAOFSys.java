package com.example.progettoispw.dao.user;

import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.exception.FSysOperationException;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;

import java.io.*;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAOFSys extends UserDAODemo implements UserDAO {

    private static final String FOLDER_NAME = "persistence";

    private static final String USER_FILE = "user.txt";

    private static final String SEPARATOR = ";";

    private boolean isLoaded = false;

    private static final Logger log = Logger.getLogger(UserDAOFSys.class.getName());

    @Override
    public List<User> getAllUsers() {

        loadAllUsers();
        return super.getAllUsers();
    }

    @Override
    public void addUser(User user) {

        //aggiorno subito la lista temporanea
        loadAllUsers();

        saveData(user);

        super.addUser(user);

    }

    @Override
    public void deleteUser(User user) {
        loadAllUsers();
        deleteUserData(user.getUsername());
        super.deleteUser(user);
    }

    @Override
    public User getUserByUsername(String username) {
        loadAllUsers();

        return super.getUserByUsername(username);
    }

    @Override
    public boolean logWithPSW(String username, String password) {

        loadAllUsers();

        return super.logWithPSW(username, password);
    }


    private void loadAllUsers()  {

        // 1. EARLY RETURN: Se i dati sono già in memoria, usciamo subito
        if (isLoaded) {
            return;
        }

        File file = getStorageFile();

        // 2. EARLY RETURN: Se il file non esiste, segniamo come caricato (vuoto) e usciamo
        if (!file.exists()) {
            isLoaded = true;
            return;
        }

        // 3. Lettura pulita: nessun annidamento mostruoso
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                processUserLine(line); // Deleghiamo la creazione!
            }
        } catch (IOException e) {
            ErrorHandler.show(new FSysOperationException(e.getMessage()));
        }

        isLoaded = true;
    }

    private void processUserLine(String line) {
        String[] parts = line.split(";");

        // EARLY RETURN: Salta la riga se è corrotta o vuota
        if (parts.length < 3) {
            return;
        }

        String username = parts[0];
        String password = parts[1];
        String role = parts[2];

        // IL TOCCO DA MAESTRO: L'operatore ternario (?)
        // Sostituisce l'intero blocco if/else in una sola riga leggibile
        UserType type = role.equals("SELLER") ? UserType.SELLER : UserType.BUYER;

        // Creiamo e aggiungiamo l'utente
        User u = new User(username, password, type);
        super.addUser(u);
    }

    // ------------------------------------------------------------
    // METODO HELPER: Gestisce cartella e file automaticamente
    // ------------------------------------------------------------
    private File getStorageFile()  {
        // 1. Determina il percorso corrente
        File folder = new File(FOLDER_NAME);

        // 2. Crea la cartella se non esiste
        if (!folder.exists()) {
            boolean created = folder.mkdir();
            if (created) log.log(Level.INFO, "DEBUG: Cartella 'persistence' creata.");
        }

        // 3. Ritorna il file (verrà creato automaticamente dal writer se manca)
        return new File(folder, USER_FILE);
    }


    //HELPER PER SALVARE LE MODIFICHE APPORTATE
    public void saveData(User user) {
        File file = getStorageFile();

        // true nel costruttore = APPEND MODE (Aggiunge in fondo senza cancellare)
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {

            StringBuilder sb = new StringBuilder();
            sb.append(user.getUsername()).append(SEPARATOR);
            sb.append(user.getPassword()).append(SEPARATOR);

            // Gestione Ruolo
            if (user instanceof Seller) sb.append("SELLER");
            else sb.append("BUYER");

            bw.write(sb.toString());
            bw.newLine(); // A capo per il prossimo utente

            log.log(Level.INFO, "DEBUG: Scritta riga TXT per {0}" , user.getUsername());
        } catch (IOException e) {
            ErrorHandler.show(new FSysOperationException(e.getMessage()));
        }
    }

    public void deleteUserData(String username) {
        File inputFile = getStorageFile();
        // Creiamo un file temporaneo affianco a quello originale
        File tempFile = new File(inputFile.getAbsolutePath() + ".tmp");

        boolean isDeleted = false;

        // Apriamo ENTRAMBI i file (uno legge, l'altro scrive da zero)
        try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
             BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile, false))) { // false = OVERWRITE MODE

            String currentLine;

            // Leggiamo riga per riga
            while ((currentLine = br.readLine()) != null) {

                // Controlliamo se la riga inizia con l'username cercato + il separatore
                // (evita bug se cerchi "Mario" e cancella anche "MarioRossi")
                if (currentLine.startsWith(username + SEPARATOR)) {
                    isDeleted = true;
                    continue; // SALTIAMO LA SCRITTURA! L'utente svanisce.
                }

                // Se non è l'utente da cancellare, ricopiamo la riga nel nuovo file
                bw.write(currentLine);
                bw.newLine();
            }

        } catch (IOException e) {
            ErrorHandler.show(new FSysOperationException("Errore in lettura/scrittura: " + e.getMessage()));
            return; // Interrompiamo tutto in caso di errore
        }

        // FASE FINALE: Lo scambio dei file
        if (isDeleted) {
            // Eliminiamo il vecchio file con l'utente indesiderato
            if (!inputFile.delete()) {
                ErrorHandler.show(new FSysOperationException("Impossibile eliminare il file originale."));
                return;
            }
            // Rinominiamo il temp file per farlo diventare il nuovo file ufficiale
            if (!tempFile.renameTo(inputFile)) {
                ErrorHandler.show(new FSysOperationException("Impossibile rinominare il file temporaneo."));
            } else {
                log.log(Level.INFO, "DEBUG: Utente {0} cancellato con successo dal TXT", username);
            }
        } else {
            // Se non l'abbiamo trovato, non serve sostituire i file, eliminiamo solo il temp

            if(!tempFile.delete()){
                log.log(Level.SEVERE, "Impossibile eliminare il file temporaneo.");
            }
            log.log(Level.WARNING, "DEBUG: Utente {0} non trovato nel file TXT", username);
        }
    }
}
