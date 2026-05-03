package com.example.progettoispw.controller.graphic;


import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.InvalidInputMessages;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.Gradation;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class SellerCatalogGraphicController implements Initializable {

    @FXML private Label lblShopName;
    @FXML private TableView<CollectableCardBean> tableCatalog;
    @FXML private TableColumn<CollectableCardBean, String> colName;
    @FXML private TableColumn<CollectableCardBean, Float> colPrice;
    @FXML private TableColumn<CollectableCardBean, Gradation> colGrade;
    @FXML private TableColumn<CollectableCardBean, String> colType;
    @FXML private TableColumn<CollectableCardBean, String> colAttribute;
    @FXML private TableColumn<CollectableCardBean, Integer> colLevel;

    private final ManageCatalogController logicController = new ManageCatalogController(new ManageNotificationsController());

    private SceneManager sceneManager;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.sceneManager = new SceneManager();

        User currentSeller = SessionManager.getInstance().getLoggedUser();

        UserBean userBean = new UserBean(currentSeller.getUsername(), currentSeller.getPassword());

        lblShopName.setText("Catalogo di: " + currentSeller.getUsername());

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        colGrade.setCellValueFactory(new PropertyValueFactory<>("gradation"));
        colType.setCellValueFactory(new PropertyValueFactory<>("type"));
        colAttribute.setCellValueFactory(new PropertyValueFactory<>("attribute"));
        colLevel.setCellValueFactory(new PropertyValueFactory<>("level"));

        refreshTable(userBean);
    }

    private void refreshTable(UserBean userBean) {

        ObservableList<CollectableCardBean> cardList; // La lista che la tabella "osserva"

        List<CollectableCardBean> cards = logicController.getSellerCards(userBean);
        cardList = FXCollections.observableArrayList(cards);
        tableCatalog.setItems(cardList);
    }


    @FXML
    public void onAddCardClick(ActionEvent event){

        sceneManager.startScene(event, "/GUI/AddCard.fxml");

    }

    @FXML
    public void onBackClick(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/Home.fxml");

    }


    @FXML
    public void onEditPriceClick(ActionEvent event) {

        CollectableCardBean selected = tableCatalog.getSelectionModel().getSelectedItem();

        if (selected == null) {
            ErrorHandler.show(new OperationFailedException("Seleziona una carta dalla tabella per modificarne il prezzo"));
            return;
        }

        TextInputDialog dialog = new TextInputDialog(String.valueOf(selected.getPrice()));
        dialog.setTitle("Modifica Prezzo");
        dialog.setHeaderText("Modifica prezzo per: " + selected.getName());
        dialog.setContentText("Nuovo Prezzo (€):");

        Optional<String> result = dialog.showAndWait();

        result.ifPresent(newPriceStr -> {
            try {
                Float newPrice = Float.parseFloat(newPriceStr);

                logicController.updateCardPrice(selected, newPrice);

                tableCatalog.refresh();

            } catch (BaseException e) {
                ErrorHandler.show(new InvalidInputException(InvalidInputMessages.UPDATE_FAIL));
            }
        });
    }

}
