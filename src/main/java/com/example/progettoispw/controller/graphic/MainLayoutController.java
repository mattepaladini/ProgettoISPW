package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.session.SessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainLayoutController {

    private static final Logger logger = Logger.getLogger(MainLayoutController.class.getName());
    public static final String GUI_HOME_FXML = "/GUI/Home.fxml";
    private static final SceneManager sceneManager = new SceneManager();

    // Riferimento all'area centrale del BorderPane
    @FXML
    private StackPane centerPane;

    @FXML
    private VBox sideBar;


    // Mi servono per oscurare i bottoni relativi alla pagina in cui mi trovo
    @FXML private javafx.scene.control.Button btnHome;
    @FXML private javafx.scene.control.Button btnSearch;
    @FXML private javafx.scene.control.Button btnSell;
    @FXML private javafx.scene.control.Button btnProfile;

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
            btnProfile.setOnAction(this::logout);
        }

        // Appena apro l'app, carico subito la Home
        loadPage(GUI_HOME_FXML);
    }

    private void doLogin(ActionEvent event) {
        sceneManager.startScene(event , "/GUI/Login.fxml");

    }

    private void logout(ActionEvent event) {
        SessionManager.getInstance().logout();

        sceneManager.startScene(event, GUI_HOME_FXML);

    }

    @FXML
    public void goToSearch(ActionEvent event) {
        loadPage("/GUI/Search.fxml");
    }

    @FXML
    public void showHome(ActionEvent event) {
        loadPage(GUI_HOME_FXML);
    }


    @FXML
    public void showSell(ActionEvent event) {

    }

    @FXML
    public void showProfile(ActionEvent event) {

    }

    @FXML
    public void doLogout(ActionEvent event) {

        logger.log(Level.INFO, "Logout");
    }


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
            logger.log(Level.SEVERE, "impossibile caricare la vista: {0}",fxmlPath);
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
