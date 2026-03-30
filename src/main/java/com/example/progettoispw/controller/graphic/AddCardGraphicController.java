package com.example.progettoispw.controller.graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Gradazione;
import com.example.progettoispw.model.Type;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AddCardGraphicController implements Initializable {

    @FXML private TextField nameField;
    @FXML private TextField priceField;
    @FXML private ComboBox<Gradazione> gradeComboBox; // Tipizzato con l'Enum
    @FXML private ComboBox<Attribute> attributeComboBox;
    @FXML private ComboBox<Type> typeComboBox;
    @FXML private TextField levelField;

    private static final Logger logger = Logger.getLogger(AddCardGraphicController.class.getName());
    private SceneManager sceneManager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        gradeComboBox.setItems(FXCollections.observableArrayList(Gradazione.values()));
        attributeComboBox.setItems(FXCollections.observableArrayList(Attribute.values()));
        typeComboBox.setItems(FXCollections.observableArrayList(Type.values()));

        this.sceneManager = new SceneManager();
    }

    @FXML
    public void onCancel(ActionEvent event) {
        goBackToCatalog(event);
    }

    @FXML
    public void onSave(ActionEvent event) {

        try{

            CollectableCardBean newBeanCard = new CollectableCardBean();

            newBeanCard.setNomeCarta(nameField.getText());

            newBeanCard.setPrezzoCorrente(Float.parseFloat(priceField.getText()));
            newBeanCard.setLivello(Integer.parseInt(levelField.getText()));

            newBeanCard.setGradazione(gradeComboBox.getValue());
            newBeanCard.setTipo(typeComboBox.getValue());
            newBeanCard.setAttributo(attributeComboBox.getValue());

            User logeduser =  SessionManager.getInstance().getLoggedUser();

            ManageCatalogController logicController = new ManageCatalogController();
            logicController.addCard(newBeanCard,logeduser );

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ToastManager.showToast(stage,"Carta aggiunta con successo");
            logger.log(Level.INFO,"Carta aggiunta con successo");

            goBackToCatalog(event);

        } catch (BaseException e) {
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            ToastManager.showErrorToast(stage, e.getMessage());
        }


    }

    private void goBackToCatalog(ActionEvent event) {

        sceneManager.startScene(event, "/GUI/SellerCatalog.fxml");

    }


}
