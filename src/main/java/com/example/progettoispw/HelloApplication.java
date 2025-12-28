package com.example.progettoispw;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;

public class HelloApplication extends Application {
    // Impostiamo la variabile a false per far partire il sistema in modalità Light
    private boolean isDark = false;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/MainLayout.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, 800, 600);

        // 1. Carica il CSS Base (struttura)
        scene.getStylesheets().add(getClass().getResource("/com/example/progettoispw/GUI/style.css").toExternalForm());

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
    }

    /**
     * Metodo riscritto per gestire correttamente la pulizia dei temi
     * e l'applicazione della modalità selezionata.
     */


    private void updateTheme(Scene scene) {
        // Rimuoviamo eventuali temi precedenti per evitare sovrapposizioni cromatiche
        scene.getStylesheets().removeIf(s -> s.contains("/com/example/progettoispw/GUI/light-theme.css") || s.contains("/com/example/progettoispw/GUI/dark-theme.css"));

        // Determiniamo quale file caricare in base allo stato di isDark
        String themeFile = isDark ? "/com/example/progettoispw/GUI/dark-theme.css" : "/com/example/progettoispw/GUI/light-theme.css";

        try {
            String cssPath = getClass().getResource(themeFile).toExternalForm();
            scene.getStylesheets().add(cssPath);
        } catch (NullPointerException e) {
            System.err.println("Errore: Impossibile trovare il file " + themeFile);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
