package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.session.SessionManager;
import com.example.progettoispw.model.UserType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import com.example.progettoispw.model.User;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeGraphicController implements Initializable {


    @FXML
    private Button btnVendi;
    @FXML
    private Button btnCompra;
    @FXML
    private Button btnCarrello;

    private final SceneManager sceneManager = new SceneManager();

    @FXML
    public void doLogout() {
        SessionManager.getInstance().logout();
    }


    @FXML
    public void onSearchClick(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/Search.fxml");

        /*
        try {
            // 1. Carico la CORNICE (MainLayout)
            // Assicurati che il path sia corretto
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // 2. Carico il CONTENUTO (SellerCatalog)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/GUI/Search.fxml"));
            // Uso 'Node' perché il contenuto è la root del file FXML (es. VBox o AnchorPane)
            Node catalogNode = contentLoader.load();

            // 3. INIEZIONE: Metto il catalogo al CENTRO del MainLayout
            rootLayout.setCenter(catalogNode);

            // 4. Mostro la scena combinata
            // Mantengo le dimensioni fisse per evitare il restringimento della finestra
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Sell.fxml");
        }*/
    }

    @FXML
    public void onProfileClick(ActionEvent event) throws IOException {

        // 1. RECUPERO L'UTENTE DALLA SESSIONE
        User currentUser = SessionManager.getInstance().getLoggedUser();

        String fxmlFile = "";
        if (currentUser == null) {
            fxmlFile = "/GUI/Login.fxml";
        } else {

            SessionManager.getInstance().logout();
            System.out.println("Utente uscito");

            fxmlFile = "/GUI/Home.fxml";
        }

        sceneManager.startScene(event, fxmlFile);

        /*
        // 2. Eseguo il caricamento "Cornice + Contenuto"
        try {
            // A. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // B. Carico il CONTENUTO (Login o Profilo)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            // Uso 'Node' perché il contenuto potrebbe essere VBox, AnchorPane o altro
            Node contentNode = contentLoader.load();

            // C. INIEZIONE: Metto il contenuto al centro della cornice
            rootLayout.setCenter(contentNode);

            // D. Preparo la scena
            // Manteniamo le dimensioni fisse come ci siamo detti
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Login.fxml");
        }*/


    }

    @FXML
    public void onSellPageClicked(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/SellerCatalog.fxml");

        /*
        try {
            // 1. Carico la CORNICE (MainLayout)
            // Assicurati che il path sia corretto
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // 2. Carico il CONTENUTO (SellerCatalog)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/GUI/SellerCatalog.fxml"));
            // Uso 'Node' perché il contenuto è la root del file FXML (es. VBox o AnchorPane)
            Node catalogNode = contentLoader.load();

            // 3. INIEZIONE: Metto il catalogo al CENTRO del MainLayout
            rootLayout.setCenter(catalogNode);

            // 4. Mostro la scena combinata
            // Mantengo le dimensioni fisse per evitare il restringimento della finestra
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Sell.fxml");
        }*/
    }

    @FXML
    public void onBuyCards(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/SellerCatalog.fxml");

        /*
        try {
            // 1. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            //Carico il CONTENUTO (SellerCatalog)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource("/GUI/SellerCatalog.fxml"));

            Node catalogNode = contentLoader.load();

            rootLayout.setCenter(catalogNode);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Buy.fxml");
        }*/
    }

    @FXML
    public void onCartClick(ActionEvent event) {


        String fxmlFile = "";
        if (SessionManager.getInstance().getLoggedUser() == null) {

            //mando l'utente alla schermata Login
            fxmlFile = "/GUI/Login.fxml";
        } else {
            fxmlFile = "/GUI/Cart.fxml";
        }

        sceneManager.startScene(event, fxmlFile);

        /*
        // 2. Eseguo il caricamento "Cornice + Contenuto"
        try {
            // A. Carico la CORNICE (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // B. Carico il CONTENUTO (Login o Profilo)
            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlFile));
            // Uso 'Node' perché il contenuto potrebbe essere VBox, AnchorPane o altro
            Node contentNode = contentLoader.load();

            // C. INIEZIONE: Metto il contenuto al centro della cornice
            rootLayout.setCenter(contentNode);

            // D. Preparo la scena
            // Manteniamo le dimensioni fisse come ci siamo detti
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);

            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new loadPageException("Impossibile caricare la pagina Login.fxml");
        }*/

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        User user = SessionManager.getInstance().getLoggedUser();

        btnVendi.setVisible(false);
        btnCompra.setVisible(false);
        btnCarrello.setVisible(false);

        User currentUser = SessionManager.getInstance().getLoggedUser();
        if (currentUser != null) {
            if (currentUser.getTipoUtente().equals(UserType.SELLER)) {
                btnVendi.setVisible(true);
            } else if (currentUser.getTipoUtente().equals(UserType.BUYER)) {
                btnCompra.setVisible(true);
                btnCarrello.setVisible(true);
            }

        }

    }

}

