package com.example.progettoispw;

import com.example.progettoispw.Controller.CLI.HomeCLI;
import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.PersistenceType;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.DataBase.DBConnection;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class HelloApplication extends Application {
    // Impostiamo la variabile a false per far partire il sistema in modalità Light
    private boolean isDark = false;

    public static Logger logger = Logger.getLogger(HelloApplication.class.getName());

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
        int choicePersistence = -1;
        int choiceView = -1;


        System.out.println("-".repeat(105));
        System.out.println(" SISTEMA DI CONFIGURAZIONE AVVIO");
        System.out.println("-".repeat(105));
        System.out.println("Scegli la modalità di persistenza dei dati:");
        System.out.println("1. Database (MySQL/JDBC)");
        System.out.println("2. DEMO (Salvataggio temporaneo)");
        System.out.println("3. FSYS (Salvataggio su file, solo per Venditori)");
        System.out.println("-".repeat(105));

        // Ciclo finché l'utente non inserisce un valore valido
        while (choicePersistence != 1 && choicePersistence != 2 && choicePersistence != 3) {
            System.out.print("Inserisci la tua scelta (1 o 2 o 3): ");

            if (scanner.hasNextInt()) {
                choicePersistence = scanner.nextInt();

                switch (choicePersistence) {
                    case 1:
                        System.out.println(">> Modalità selezionata: DATABASE (JDBC)");

                        Connection testConn = DBConnection.getInstance().getConnection();

                        if (testConn == null) {
                            logger.log(Level.SEVERE, "Impossibile trovare il database");
                        }
                        DAOFactory.setPersistenceType(PersistenceType.JDBC);
                        break;

                        case 2:
                            System.out.println(">> Modalità selezionata: DEMO");
                            DAOFactory.setPersistenceType(PersistenceType.DEMO);
                            break;

                            case 3:
                                System.out.println(">> Modalità selezionata: FSYS");
                                DAOFactory.setPersistenceType(PersistenceType.FSYS);

                    default:
                        logger.log(Level.SEVERE, "Inserire una scelta valida");
                }

            } else {
                logger.log(Level.SEVERE, "Inserire una scelta valida");
                scanner.next(); // Consuma l'input errato per evitare loop infiniti
            }
        }

        System.out.println("-".repeat(105));
        System.out.println(" SISTEMA DI CONFIGURAZIONE AVVIO");
        System.out.println("-".repeat(105));
        System.out.println("Scegli come usare il sistema:");
        System.out.println("1. Grafica UI");
        System.out.println("2. Command User Interface (CLI)");
        System.out.println("-".repeat(105));

        while(choiceView != 1 && choiceView != 2) {
            System.out.print("Inserisci la tua scelta (1 o 2): ");

            if (scanner.hasNextInt()) {
                choiceView = scanner.nextInt();

                switch (choiceView) {
                    case 1:
                        System.out.println(">> Grafica UI");
                        launch();
                        break;

                        case 2:
                            System.out.println(">> Command User Interface (CLI)");
                            HomeCLI homeCLI = new HomeCLI();
                            homeCLI.startHomePage();
                }

            }
        }

    }

    public static void main(String[] args) {
        chooseConf();
    }
    //
    //
}