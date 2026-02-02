package com.example.progettoispw.DAO.User;

import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAOFSys implements UserDAO {

    private List<User> memoryUser = new ArrayList<>();

    private static final String FOLDER_NAME = "persistence";

    private static final String USER_FILE = "user.txt";

    private static final String SEPARATOR = ";";

    public UserDAOFSys() {}

    @Override
    public List<User> getAllUsers() {

        if(memoryUser == null) {
            memoryUser = loadAllUsers();
        }
        return memoryUser;
    }

    @Override
    public void addUser(User user) {

        //aggiorno subito la lista temporanea
        loadAllUsers();
        memoryUser.add(user);

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

    @Override
    public User getUserByUsername(String username) {
        for (User u : loadAllUsers()) {
            if (u.getUsername().equals(username)) return u;
        }
        return null;
    }

    @Override
    public boolean logWithPSW(String Username, String password) {

        List<User> users = loadAllUsers();
        for(User u : users) {
            if(u.getUsername().equals(Username) && u.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }


    private List<User> loadAllUsers()  {

        if(!memoryUser.isEmpty()){
            System.out.println(memoryUser.size());
            return memoryUser;
        }

        File file = getStorageFile();

        if(!file.exists()) {return null;}
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

                memoryUser.add(u);

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return memoryUser;
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
    public void saveData(List<User> users)  {
        File file = getStorageFile();

        System.out.println("--------------------------------------------------");
        System.out.println("[DEBUG USER DAO] Inizio salvataggio utenti.");
        System.out.println("[DEBUG USER DAO] Percorso file target: " + file.getAbsolutePath());
        System.out.println("[DEBUG USER DAO] Numero utenti da salvare: " + users.size());

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(file))) {
            oos.writeObject(users);
            System.out.println("[DEBUG USER DAO] SCRITTURA RIUSCITA! Il file dovrebbe esistere ora.");
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Impossibile salvare su file: " + file.getAbsolutePath());
            System.err.println("Messaggio errore: " + e.getMessage());
        }
    }

}
