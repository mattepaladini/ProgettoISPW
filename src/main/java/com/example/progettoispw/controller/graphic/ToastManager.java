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

        Popup popup = new Popup();
        popup.setAutoFix(true);
        popup.setHideOnEscape(true);

        Label label = new Label(message);
        label.setStyle("-fx-background-color: #4CAF50; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");

        popup.getContent().add(label);

        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
        });

        popup.show(stage);

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
        popup.setHideOnEscape(true);

        HBox hbox = new HBox(15);
        hbox.setAlignment(Pos.CENTER);
        hbox.setStyle("-fx-background-color: #F44336; " +
                "-fx-padding: 10px 20px; " +
                "-fx-background-radius: 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");


        Label messageLabel = new Label(message);
        messageLabel.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold;");


        Label closeBtn = new Label("✖");
        closeBtn.setStyle("-fx-text-fill: white; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-cursor: hand;");


        closeBtn.setOnMouseClicked(e -> popup.hide());

        closeBtn.setOnMouseEntered(e -> closeBtn.setStyle("-fx-text-fill: #FFCDD2; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));
        closeBtn.setOnMouseExited(e -> closeBtn.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold; -fx-cursor: hand;"));

        hbox.getChildren().addAll(messageLabel, closeBtn);
        popup.getContent().add(hbox);

        popup.setOnShown(e -> {
            popup.setX(stage.getX() + stage.getWidth() / 2 - popup.getWidth() / 2);
            popup.setY(stage.getY() + stage.getHeight() / 2 - popup.getHeight() / 2);
        });

        popup.show(stage);
    }

}
