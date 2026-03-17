package com.example.progettoispw.controller.graphic;

import javafx.animation.PauseTransition;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;

public class ToastManager {

    private ToastManager(){}

    public static void showToast(Stage stage, String message) {
        // 1. Creiamo il popup nativo (è una finestra trasparente senza bordi)
        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);

        // 2. Creiamo la grafica del nostro Toast
        Label label = new Label(message);
        label.setStyle("-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");

        popup.getContent().add(label);

        // 3. Calcoliamo la posizione centrale in basso (si attiva appena il popup viene mostrato)
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
        });

        // 4. Mostriamo il popup sulla finestra corrente
        popup.show(stage);

        // 5. Impostiamo il timer di "autodistruzione" a 2.5 secondi
        PauseTransition delay = new PauseTransition(Duration.seconds(2.5));
        delay.setOnFinished(e -> popup.hide());
        delay.play();
    }

    // ----------------------------------------------------------------
    // TOAST DI ERRORE
    // ----------------------------------------------------------------
    public static void showErrorToast(Stage stage, String message) {
        Popup popup = new Popup();
        popup.setAutoFix(true);
        // Nasconde il popup se l'utente preme il tasto ESC sulla tastiera
        popup.setHideOnEscape(true);

        // 1. Creiamo un contenitore orizzontale per il testo e la 'X'
        HBox hbox = new HBox(15); // 15px di spazio tra testo e pulsante
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #F44336; " + // Rosso Errore
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");

        // 2. La Label per il messaggio
        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;");

        // 3. La Label che fa da pulsante di chiusura (una 'X')
        Label closeBtn = new Label("✖");
        closeBtn.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;"); // Cambia il cursore nella "manina" quando ci passi sopra

        // 4. L'azione che chiude il popup quando si clicca la 'X'
        closeBtn.setOnMouseClicked(e -> popup.hide());

        // (Opzionale) Un piccolo effetto hover per far capire che la X è cliccabile
        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle("-fx-text-fill: #FFCDD2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));

        // 5. Aggiungiamo testo e pulsante al contenitore, e il contenitore al popup
        hbox.getChildren().addAll(messageLabel, closeBtn);
        popup.getContent().add(hbox);

        // 6. Centriamo il popup (stessa logica di prima)
        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
        });

        // 7. Mostriamo il popup (NIENTE PauseTransition, così non scompare!)
        popup.show(stage);
    }

}
