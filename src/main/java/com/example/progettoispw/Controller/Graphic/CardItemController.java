package com.example.progettoispw.Controller.Graphic;

import com.example.progettoispw.Controller.Logic.BuyController;
import com.example.progettoispw.bean.CollectableCardBean;

//SI OCCUPA DI GESTIRE LE SINGOLE RIGHE DI UNA RICERCA

public class CardItemController {

    private CollectableCardBean myCard;
    private BuyController logicController;

    public void setCardData(CollectableCardBean card, BuyController logicController) {
        this.myCard = card;
        this.logicController = logicController;

        // Imposta i testi grafici
        //lblNome.setText(card.getNomeCarta());
        //lblPrezzo.setText(String.valueOf(card.getPrezzo()));
        // ... ecc ...
    }

    /*
    @FXML
    public void onBuyClick(ActionEvent event) {
        // Quando clicco "Acquista" sulla riga, notifico il controller logico
        // dicendogli: "L'utente ha scelto QUESTA carta qui".

        // Fase 2 del flusso: Selezione
        logicController.selectCard(this.myCard);

        // Ora potresti navigare verso la schermata di Checkout/Pagamento
        // o aprire un popup di conferma.
        System.out.println("Selezionata carta: " + myCard.getNomeCarta());
    }
*/
}
