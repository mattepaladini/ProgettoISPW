package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.exception.LoadPageException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneManager {

    String fxmlMainPage="/GUI/MainLayout.fxml";

    public void startScene(ActionEvent event, String specificPage){

        try {
            // 1. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(fxmlMainPage));
            BorderPane rootLayout = mainLoader.load();

            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(specificPage));

            Node catalogNode = contentLoader.load();

            rootLayout.setCenter(catalogNode);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new LoadPageException("Impossibile caricare la pagina" +  specificPage);
        }
    }


    // Nuovo metodo nel SceneManager: fa tutto quello che faceva l'altro, ma restituisce il Controller!
    public <T> T startSceneAndGetController(ActionEvent event, String specificPage) {
        try {
            // 1. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource(fxmlMainPage));
            BorderPane rootLayout = mainLoader.load();

            // 2. Carico il contenuto specifico
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(specificPage));
            Node contentNode = contentLoader.load();

            // 3. Inserisco il contenuto al centro
            rootLayout.setCenter(contentNode);

            // 4. Mostro la scena
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);
            stage.setScene(scene);
            stage.show();

            // 5. LA MAGIA: Restituisco il controller appena creato!
            return contentLoader.getController();

        } catch (IOException e) {
            throw new LoadPageException("Impossibile caricare la pagina " + specificPage);
        }
    }

}
