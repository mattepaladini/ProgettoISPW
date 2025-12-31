package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.bean.CollectableCardBean;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.util.List;



public class CartGraphicController {
    @FXML private VBox cartItemsContainer;
    @FXML private Label lblTotalPrice;
    @FXML private Label lblItemCount;
    @FXML private Label lblEmptyCart;
    @FXML private Button btnCheckout;

    // Metodo chiamato quando si carica la schermata
    // Ipotizziamo che riceva la lista di carte nel carrello
    public void initData(List<CollectableCardBean> cartItems) {

        cartItemsContainer.getChildren().clear();
        double total = 0.0;

        // 1. Gestione Carrello Vuoto
        if (cartItems == null || cartItems.isEmpty()) {
            lblEmptyCart.setVisible(true);
            btnCheckout.setDisable(true); // Non puoi pagare se è vuoto
            updateSummary(0, 0.0);
            return;
        } else {
            lblEmptyCart.setVisible(false);
            btnCheckout.setDisable(false);
        }

        // 2. Popolamento Lista (Riutilizzo di CardItem.fxml!)
        try {
            for (CollectableCardBean card : cartItems) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/progettoispw/GUI/CardItem.fxml"));
                Parent cardNode = loader.load();

                // Nota: stiamo usando lo stesso controller della ricerca.
                // In un sistema reale, il bottone "Acquista" dovrebbe diventare "Rimuovi".
                // Per ora lo lasciamo così per semplicità.
                CardItemController itemController = loader.getController();
                // Passiamo null come controller logico per ora, o il CartController se serve
                itemController.setCardData(card, null);

                cartItemsContainer.getChildren().add(cardNode);

                // Calcolo somma
                total += card.getPrezzoCorrente();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 3. Aggiornamento Footer
        updateSummary(cartItems.size(), total);
    }

    private void updateSummary(int count, double total) {
        lblItemCount.setText(count + (count == 1 ? " articolo" : " articoli"));
        lblTotalPrice.setText(String.format("€ %.2f", total));
    }

    @FXML
    public void onCheckoutClick() {
        System.out.println("Navigazione verso la schermata di pagamento...");
        // Qui chiameresti il MainLayoutController per cambiare scena
    }
}
