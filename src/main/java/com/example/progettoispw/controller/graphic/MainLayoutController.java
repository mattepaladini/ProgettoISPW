package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.session.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.event.ActionEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainLayoutController {

    private static final Logger logger = Logger.getLogger(MainLayoutController.class.getName());
    public static final String GUI_HOME_FXML = "/GUI/Home.fxml";

    // Riferimento all'area centrale del BorderPane
    @FXML
    private StackPane centerPane;

    @FXML
    private VBox sideBar;

    @FXML private BorderPane borderPane;

    // Mi servono per oscurare i bottoni relativi alla pagina in cui mi trovo
    @FXML private javafx.scene.control.Button btnHome;
    @FXML private javafx.scene.control.Button btnSearch;
    @FXML private javafx.scene.control.Button btnSell;
    @FXML private javafx.scene.control.Button btnProfile;

    /*
    // Metodo riutilizzabile per cambiare il centro della pagina
    public void setCenterContent(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();
            borderPane.setCenter(newView);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Impossibile caricare la vista: "+fxmlPath, e);
        }
    }*/

    // Metodo che viene chiamato appena il layout è caricato
    @FXML
    public void initialize() {

        if (centerPane == null) {
            logger.log(Level.SEVERE, "Errore nella caricatura");
            return;
        }

        if(SessionManager.getInstance().getLoggedUser()==null){
            btnProfile.setText("Login");
            btnProfile.setOnAction(this::doLogin);
        } else {
            btnProfile.setText("Logout");
            btnProfile.setOnAction(this::Logout);
        }

        // Appena apro l'app, carico subito la Home
        loadPage(GUI_HOME_FXML);
    }

    private void doLogin(ActionEvent event) {
        navigateTo(event, "/GUI/Login.fxml");
    }

    private void Logout(ActionEvent event) {
        SessionManager.getInstance().logout();

        navigateTo(event, GUI_HOME_FXML);
    }

    private void navigateTo(ActionEvent event, String fxmlPath) {
        try {
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane root = mainLoader.load();

            FXMLLoader contentLoader = new FXMLLoader(getClass().getResource(fxmlPath));
            Node contentNode = contentLoader.load();

            root.setCenter(contentNode);

            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 800, 600);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.log(Level.WARNING, e.getMessage(), e);
        }
    }

    // --- AZIONI DELLA NAVBAR ---

    @FXML
    public void goToSearch(ActionEvent event) {
        // Qui ci colleghiamo finalmente a Search.fxml (che avrà la barra di ricerca)
        //navigateTo(event, "/GUI/Search.fxml");
        loadPage("/GUI/Search.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadPage(GUI_HOME_FXML);
    }

    @FXML
    public void showBuy(ActionEvent event) {
        //loadPage("/fxml/BuyCards.fxml");
    }

    @FXML
    public void showSell(ActionEvent event) {
        //loadPage("/fxml/SellCards.fxml");
    }

    @FXML
    public void showProfile(ActionEvent event) {
        //loadPage("/fxml/Profile.fxml");
    }

    @FXML
    public void doLogout(ActionEvent event) {
        // Qui dovresti cambiare l'intera Scena per tornare al Login
        // Perché il Login non ha la navbar!
        logger.log(Level.INFO, "Logout");
    }

    // --- METODO UTILITY PER CARICARE LE PAGINE ---

    private void loadPage(String fxmlPath) {
        try {
            // 1. Carico il file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newView = loader.load();

            // 2. Pulisco l'area centrale
            centerPane.getChildren().clear();

            // 3. Aggiungo la nuova vista
            centerPane.getChildren().add(newView);

            // --- TRUCCO PER NASCONDERE LA NAVBAR ---

            if (fxmlPath.equals(GUI_HOME_FXML)) {
                // SE È LA HOME: Nascondi la barra
                sideBar.setVisible(false);
                sideBar.setManaged(false); // Questo fa "collassare" lo spazio, così la Home si allarga
            } else {
                // SE È UN'ALTRA PAGINA: Mostra la barra
                sideBar.setVisible(true);
                sideBar.setManaged(true);

                updateSidebarButtons(fxmlPath);
            }

        } catch (IOException e) {
            logger.log(Level.SEVERE, "impossibile caricare la vista: "+fxmlPath, e);
        }
    }

    /**
     * Metodo che gestisce quale bottone nascondere
     */
    private void updateSidebarButtons(String currentPath) {

        switch (currentPath) {
            case "/GUI/Search.fxml":
                btnSearch.setVisible(false);
                break;
            case "/GUI/Sell.fxml":
                btnSell.setVisible(false);
                break;
            case "/GUI/Profile.fxml":
                btnProfile.setVisible(false);
                break;

            default:

            // Aggiungi altri casi se hai altre pagine
        }
    }


}
