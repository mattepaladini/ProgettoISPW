package com.example.progettoispw.controller.graphic;


import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.bean.CollectableCardBean;


import com.example.progettoispw.session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.Gradazione;
import com.example.progettoispw.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SellerCatalogGraphicController implements Initializable {

    @FXML private Label lblShopName;
    @FXML private TableView<CollectableCardBean> tableCatalog;
    @FXML private TableColumn<CollectableCardBean, String> colName;
    @FXML private TableColumn<CollectableCardBean, Float> colPrice;
    @FXML private TableColumn<CollectableCardBean, Gradazione> colGrade;
    @FXML private TableColumn<CollectableCardBean, String> colType;
    @FXML private TableColumn<CollectableCardBean, String> colAttribute;
    @FXML private TableColumn<CollectableCardBean, Integer> colLevel;

    private ManageCatalogController logicController;
    private ObservableList<CollectableCardBean> cardList; // La lista che la tabella "osserva"

    private static final Logger logger = Logger.getLogger(SellerCatalogGraphicController.class.getName());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        logicController = new ManageCatalogController();

        // 1. Recupero il Seller dalla sessione
        User currentSeller = SessionManager.getInstance().getLoggedUser();

        UserBean userBean = new UserBean(currentSeller.getUsername(), currentSeller.getPassword());

        lblShopName.setText("Catalogo di: " + currentSeller.getUsername());

        // 2. Configuro le colonne della tabella
        // Le stringhe devono coincidere ESATTAMENTE con i nomi degli attributi nel CardBean
        // Es: se in CardBean hai "cardName", qui scrivi "cardName"
        colName.setCellValueFactory(new PropertyValueFactory<>("nomeCarta"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("prezzoCorrente"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("gradazione")); // o "cardGrade"

        // NUOVI COLLEGAMENTI
        colType.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        colAttribute.setCellValueFactory(new PropertyValueFactory<>("attributo"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("livello"));

        // 3. Carico i dati
        refreshTable(userBean);
    }

    private void refreshTable(UserBean userBean) {
        List<CollectableCardBean> cards = logicController.getSellerCards(userBean);
        // Converto la lista normale in ObservableList per JavaFX
        cardList = FXCollections.observableArrayList(cards);
        tableCatalog.setItems(cardList);
    }


    @FXML
    public void onAddCardClick(ActionEvent event) throws IOException {
        // Qui dovrai aprire una nuova finestra (Dialog o cambio scena)
        // per inserire i dati della nuova carta.

        FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
        BorderPane root = mainLoader.load();

        FXMLLoader catalogLoader = new FXMLLoader(getClass().getResource("/GUI/AddCard.fxml"));
        Node catalogNode = catalogLoader.load();

        root.setCenter(catalogNode);

        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.getScene().setRoot(root); // Sostituisco la root della scena esistente


    }

    @FXML
    public void onBackClick(ActionEvent event) {
        try {
            // 1. Carico la Cornice (MainLayout)
            FXMLLoader mainLoader = new FXMLLoader(getClass().getResource("/GUI/MainLayout.fxml"));
            BorderPane rootLayout = mainLoader.load();

            // 2. Carico la Home
            FXMLLoader homeLoader = new FXMLLoader(getClass().getResource("/GUI/Home.fxml"));
            Node homeNode = homeLoader.load();

            // 3. Metto la Home al CENTRO
            rootLayout.setCenter(homeNode);

            // 4. Mostro la scena
            // Nota: HomeGraphicController.initialize() verrà chiamato automaticamente
            // e rileggerà la Sessione per mostrare i bottoni corretti.
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            Scene scene = new Scene(rootLayout, 800, 600);
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            logger.log(Level.WARNING, "Errore nel caricamento della Home");
        }
    }



    @FXML
    public void onEditPriceClick(ActionEvent event) {
        // 1. Prendo la carta selezionata
        CollectableCardBean selected = tableCatalog.getSelectionModel().getSelectedItem();

        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Nessuna selezione");
            alert.setContentText("Seleziona una carta dalla tabella per modificarne il prezzo.");
            alert.showAndWait();
            return;
        }

        // 2. Apro un Dialog rapido per il nuovo prezzo
        TextInputDialog dialog = new TextInputDialog(String.valueOf(selected.getPrezzoCorrente()));
        dialog.setTitle("Modifica Prezzo");
        dialog.setHeaderText("Modifica prezzo per: " + selected.getNomeCarta());
        dialog.setContentText("Nuovo Prezzo (€):");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newPriceStr -> {
            try {
                Float newPrice = Float.parseFloat(newPriceStr);

                // 3. Chiamo la logica per aggiornare
                logicController.updateCardPrice(selected, newPrice);

                // 4. Aggiorno la vista (refresh tabella)
                tableCatalog.refresh();

            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Inserisci un numero valido!");
                alert.show();
            }
        });
    }

}
