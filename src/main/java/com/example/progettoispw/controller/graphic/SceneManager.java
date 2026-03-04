package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.exception.loadPageException;
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

            //Carico il CONTENUTO (SellerCatalog)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(specificPage));

            Node catalogNode = contentLoader.load();

            rootLayout.setCenter(catalogNode);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina" +  specificPage);
        }


    }
}
