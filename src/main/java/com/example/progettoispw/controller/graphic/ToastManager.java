package com.example.progettoispw.controller.graphic;

import javafx.animation.PauseTransition;
import javafx.scene.control.Label;
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

}
