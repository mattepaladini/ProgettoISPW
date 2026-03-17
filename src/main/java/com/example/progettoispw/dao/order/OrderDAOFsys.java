package com.example.progettoispw.dao.order;

import com.example.progettoispw.exception.DatabaseOperationException;
import com.example.progettoispw.exception.ErrorHandler;
import com.example.progettoispw.exception.FSysOperationException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOFsys extends OrderDAODemo implements OrderDAO {

    private static final String FOLDER_NAME = "persistence";
    private static final String ORDER_FILE = "orders.txt";
    private static final String SEPARATOR = ";";
    private static final String CARD_SEPARATOR = ",";
    private static final String CARD_ATTR_SEPARATOR = "~"; // Separa nome carta e venditore

    private boolean isLoaded = false;

    @Override
    public List<Order> getOrdersByUser(User user) {

        loadCacheFromFile();
        return super.getOrdersByUser(user);
    }

    @Override
    public List<Order> getOrdersByID(int orderID) {
        loadCacheFromFile();
        return super.getOrdersByID(orderID);
    }

    @Override
    public void saveOrder(Order order) {

        loadCacheFromFile();

        // 1. Simuliamo l'AUTO_INCREMENT di MySQL
        int maxId = 0;
        for (Order o : this.orders) {
            if (o.getId() > maxId) {
                maxId = o.getId();
            }
        }
        order.setId(maxId + 1);

        super.saveOrder(order);

        flushToFile();

    }

    private void flushToFile() throws DatabaseOperationException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(getStorageFile()))) {

            // Scorriamo la lista 'orders' della superclasse
            for (Order o : this.orders) {
                StringBuilder sb = new StringBuilder();

                sb.append(o.getId()).append(SEPARATOR)
                        .append(o.getCompratore()).append(SEPARATOR)
                        .append(o.getDataOrdine()).append(SEPARATOR)
                        .append(o.getIndirizzoSpedizione()).append(SEPARATOR)
                        .append(o.getTotale()).append(SEPARATOR);

                // Serializziamo le carte
                List<String> carteFormattate = new ArrayList<>();
                for (Card c : o.getCarteOrdinate()) {
                    carteFormattate.add(c.getNome() + CARD_ATTR_SEPARATOR + c.getVenditore());
                }
                sb.append(String.join(CARD_SEPARATOR, carteFormattate));

                bw.write(sb.toString());
                bw.newLine();
            }

        } catch (IOException e) {
            ErrorHandler.show(new FSysOperationException(e.getMessage()));
        }
    }

    private void loadCacheFromFile() throws DatabaseOperationException {
        // Se abbiamo già letto il file, non facciamo niente
        if (isLoaded) return;

        File file = getStorageFile();
        if (!file.exists()) {
            isLoaded = true; // Il file non esiste ancora, cache vuota, tutto ok
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.trim().isEmpty()) continue;

                String[] parts = line.split(SEPARATOR);

                int id = Integer.parseInt(parts[0]);
                User compratore = new User(parts[1]);
                String data = parts[2];
                String indirizzo = parts[3];
                float totale = Float.parseFloat(parts[4]);

                Order order = new Order(id, null,  indirizzo, compratore.getUsername(),totale,data);

                // Ricostruiamo le carte se l'ordine non è vuoto
                if (parts.length > 5 && !parts[5].trim().isEmpty()) {
                    String[] carteString = parts[5].split(CARD_SEPARATOR);
                    for (String cartaDato : carteString) {
                        String[] attributiCarta = cartaDato.split(CARD_ATTR_SEPARATOR);
                        Card card = new Card(attributiCarta[0], attributiCarta[1]);     //carta creata memorizzando solo nomecarta e venditore

                        order.getCarteOrdinate().add(card);
                    }
                }

                // Aggiungiamo alla lista 'orders' ereditata da OrderDAODemo
                this.orders.add(order);
            }
            isLoaded = true; // Segniamo che il caricamento è completato

        } catch (IOException | NumberFormatException e) {
            ErrorHandler.show(new FSysOperationException(e.getMessage()));
        }
    }


    private File getStorageFile()  {
        File folder = new File(FOLDER_NAME);
        if (!folder.exists()) folder.mkdir();
        return new File(folder, ORDER_FILE);
    }
}
