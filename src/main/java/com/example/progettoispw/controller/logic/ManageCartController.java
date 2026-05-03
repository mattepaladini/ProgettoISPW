package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.utility.CardMapper;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.List;

public class ManageCartController{

    //il carrello verrà gestito in maniera "volatile" ---> mantengo una lista in RAM delle carte aggiunte al carrello
    //quando utente la logout resetto la lista

    public boolean addToCart(CollectableCardBean selectedCardBean) {

        Card selectedCard = new Card(selectedCardBean.getName(), selectedCardBean.getPrice(), selectedCardBean.getGradation(), selectedCardBean.getSeller(), selectedCardBean.getLevel(), selectedCardBean.getAttribute(), selectedCardBean.getType());
        try{
            SessionManager.getInstance().addCard(selectedCard);
            return true;

        } catch (BaseException e) {
            throw new OperationFailedException(e.getMessage());
        }

    }

    // Metodo chiamato dalla UI quando l'utente apre la pagina "Il Mio Carrello"
    public List<CollectableCardBean> getCardsFromCart() {
        //Recupero le entità dalla RAM

        return SessionManager.getInstance().getShoppingCart()
                .stream()
                .map(CardMapper::toBean)
                .toList();
    }


    public boolean removeFromCart(CollectableCardBean cartaBean) {
        try {

            Card cartaDaRimuovere = CardMapper.toEntity(cartaBean);
            SessionManager.getInstance().removeCard(cartaDaRimuovere);
            return true;
        } catch (BaseException e) {
            ErrorHandler.show(new OperationFailedException(e.getMessage()));
            return false;
        }

    }

    public float calculateCartTotal() {
        float totale = 0.0f;
        for (Card c : SessionManager.getInstance().getShoppingCart()) {
            totale += c.getPrice();
        }
        return totale;
    }

}
