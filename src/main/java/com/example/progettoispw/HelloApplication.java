package com.example.progettoispw;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.PersistenceType;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.util.Scanner;


public class HelloApplication extends Application {
    // Impostiamo la variabile a false per far partire il sistema in modalità Light
    private boolean isDark = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        // 1. Carica il CSS Base (struttura)
        scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());

        // 2. Forza l'avvio con il tema Light
        updateTheme(scene);

        // Listener per il tasto 'T' (opzionale, per continuare a testare lo switch)
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.T) {
                isDark = !isDark;
                updateTheme(scene);
                System.out.println("Switch tema eseguito. Modalità scura: " + isDark);
            }
        });

        primaryStage.setTitle("Home UI - Modalità Light predefinita");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.toFront();
        primaryStage.requestFocus();
    }
    

    private void updateTheme(Scene scene) {
        // Rimuoviamo eventuali temi precedenti per evitare sovrapposizioni cromatiche
        scene.getStylesheets().removeIf(s -> s.contains("/GUI/light-theme.css") || s.contains("/GUI/dark-theme.css"));

        // Determiniamo quale file caricare in base allo stato di isDark
        String themeFile = isDark ? "/GUI/dark-theme.css" : "/GUI/light-theme.css";

        try {
            String cssPath = getClass().getResource(themeFile).toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("Errore: Impossibile trovare il file " + themeFile);
        }
    }

    //

    public static void chooseConf(){

        Scanner scanner = new Scanner(System.in);
        int choice = -1;

        System.out.println("------------------------------------------------");
        System.out.println(" SISTEMA DI CONFIGURAZIONE AVVIO");
        System.out.println("------------------------------------------------");
        System.out.println("Scegli la modalità di persistenza dei dati:");
        System.out.println("1. Database (MySQL/JDBC)");
        System.out.println("2. DEMO (Salvataggio temporaneo)");
        System.out.println("------------------------------------------------");

        // Ciclo finché l'utente non inserisce un valore valido
        while (choice != 1 && choice != 2) {
            System.out.print("Inserisci la tua scelta (1 o 2): ");

            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();

                if (choice == 1) {
                    System.out.println(">> Modalità selezionata: DATABASE (JDBC)");
                    DAOFactory.setPersistenceType(PersistenceType.JDBC);
                } else if (choice == 2) {
                    System.out.println(">> Modalità selezionata: DEMO");
                    DAOFactory.setPersistenceType(PersistenceType.DEMO);
                } else {
                    System.out.println("!! Errore: Inserisci solo 1 o 2.");
                }
            } else {
                System.out.println("!! Errore: Input non valido. Inserisci un numero.");
                scanner.next(); // Consuma l'input errato per evitare loop infiniti
            }
        }

        System.out.println("------------------------------------------------");
        System.out.println("Avvio interfaccia grafica in corso...");
        // Non chiudiamo scanner qui perché System.in non va mai chiuso manualmente
    }

    public static void populate(){
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        User usertemp = new User("matteo", "ciao", UserType.SELLER);

        userDAO.addUser(usertemp);

        Seller seller = new Seller(usertemp.getUsername(), usertemp.getPassword());
        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();
        catalogDAO.addCatalog(new CardCatalog(seller));

        Card tempcard = new Card("Charizard", 10f, Gradazione.PERFETTO, usertemp, 001, 0, "Fuoco", null);
        catalogDAO.addCard(tempcard, seller);

    }


    public static void main(String[] args) {

        chooseConf();
        populate();
        launch();
    }
    //
    //
}