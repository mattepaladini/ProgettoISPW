package com.example.progettoispw.DAO.User;

import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOFSys extends UserDAODemo implements UserDAO {

    private static final String FOLDER_NAME = "persistence";

    private static final String USER_FILE = "user.txt";

    private static final String SEPARATOR = ";";

    private boolean isLoaded = false;

    public UserDAOFSys() {}

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
    public User getUserByUsername(String username) {
        loadAllUsers();

        return super.getUserByUsername(username);
    }

    @Override
    public boolean logWithPSW(String Username, String password) {

        loadAllUsers();

        return super.logWithPSW(Username, password);
    }


    private void loadAllUsers()  {

        if(!isLoaded) {

            File file = getStorageFile();

            if (file.exists()) {


                try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = br.readLine()) != null) {
                        String[] parts = line.split(";");
                        if (parts.length < 3) continue; // Salta righe vuote o corrotte

                        String username = parts[0];
                        String password = parts[1];
                        String role = parts[2];

                        // Ricostruisco l'oggetto
                        User u;
                        if (role.equals("SELLER")) {
                            u = new User(username, password, UserType.SELLER);

                        } else {
                            u = new User(username, password, UserType.BUYER);

                        }

                        super.addUser(u);

                    }
                } catch (IOException e) {
                    throw new RuntimeException(e.getMessage());
                }
            }
        }

        isLoaded = true;
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
            if (created) System.out.println("DEBUG: Cartella 'persistence' creata.");
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

            System.out.println("DEBUG: Scritta riga TXT per " + user.getUsername());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
