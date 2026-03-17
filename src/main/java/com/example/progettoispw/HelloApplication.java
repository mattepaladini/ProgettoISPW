package com.example.progettoispw;

import com.example.progettoispw.controller.cli.HomeCLI;
import com.example.progettoispw.dao.PersistenceType;
import com.example.progettoispw.database.DBConnection;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.Connection;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelloApplication extends Application {
    // Impostiamo la variabile a false per far partire il sistema in modalità Light
    private boolean isDark = false;

    private static boolean isCLI=false;

    private static Stage primaryStage;

    public static final Logger logger = Logger.getLogger(HelloApplication.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        // 1. Carica il CSS Base (struttura)
        scene.getStylesheets().add(getClass().getResource("/GUI/style.css").toExternalForm());

        // 2. Forza l'avvio con il tema Light
        updateTheme(scene);

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
            logger.log(Level.SEVERE, "Impossibile trovare il file");
        }
    }

    //

    public static void chooseConf(){

        Scanner scanner = new Scanner(System.in);

        setupPersistenceMode(scanner);
        setupViewMode(scanner);

    }

    private static void setupPersistenceMode(Scanner scanner) {
        System.out.println("-".repeat(105));
        System.out.println(" SISTEMA DI CONFIGURAZIONE AVVIO");
        System.out.println("-".repeat(105));
        System.out.println("Scegli la modalità di persistenza dei dati:");
        System.out.println("1. Database (MySQL/JDBC)");
        System.out.println("2. DEMO (Salvataggio temporaneo)");
        System.out.println("3. FSYS (Salvataggio su file, solo per Venditori)");
        System.out.println("-".repeat(105));

        while (true) {
            System.out.print("Inserisci la tua scelta (1 o 2 o 3): ");

            // EARLY CONTINUE: Gestiamo subito l'errore ed evitiamo l'else!
            if (!scanner.hasNextInt()) {
                logger.log(Level.SEVERE, "Inserire una scelta valida (numero intero)");
                scanner.next(); // Consuma l'input errato
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(">> Modalità selezionata: DATABASE (JDBC)");
                    Connection testConn = DBConnection.getInstance().getConnection();
                    if (testConn == null) {
                        logger.log(Level.SEVERE, "Impossibile trovare il database");
                    }
                    DAOFactory.setPersistenceType(PersistenceType.JDBC);
                    return; // Esce dal metodo e interrompe il loop (EARLY RETURN)

                case 2:
                    System.out.println(">> Modalità selezionata: DEMO");
                    DAOFactory.setPersistenceType(PersistenceType.DEMO);
                    return;

                case 3:
                    System.out.println(">> Modalità selezionata: FSYS");
                    DAOFactory.setPersistenceType(PersistenceType.FSYS);
                    return;

                default:
                    logger.log(Level.SEVERE, "Inserire una scelta valida (1, 2 o 3)");
            }
        }
    }

    private static void startCLI(){
        HomeCLI homeCLI = new HomeCLI();
        homeCLI.startHomePage();
    }

    private static void setupViewMode(Scanner scanner) {
        System.out.println("-".repeat(105));
        System.out.println(" SISTEMA DI CONFIGURAZIONE AVVIO");
        System.out.println("-".repeat(105));
        System.out.println("Scegli come usare il sistema:");
        System.out.println("1. Grafica UI");
        System.out.println("2. Command User Interface (CLI)");
        System.out.println("-".repeat(105));

        while (true) {
            System.out.print("Inserisci la tua scelta (1 o 2): ");

            if (!scanner.hasNextInt()) {
                logger.log(Level.SEVERE, "Inserire una scelta valida (numero intero)");
                scanner.next();
                continue;
            }

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println(">> Grafica UI");
                    launch();
                    return;

                case 2:
                    System.out.println(">> Command User Interface (CLI)");
                    isCLI=true;
                    startCLI();
                    return;

                default:
                    logger.log(Level.SEVERE, "Attenzione! Scelta non valida, Avvio in CLI.");
                    isCLI=true;
                    startCLI();
                    return;
            }
        }
    }

    public static boolean isCLI(){
        return isCLI;
    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }

    public static void main(String[] args) {
        chooseConf();
    }
    //
    //
}