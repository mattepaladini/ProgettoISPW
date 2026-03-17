package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.pattern.observer.CartObserver;
import com.example.progettoispw.pattern.observer.PriceObserver;
import com.example.progettoispw.session.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class ManageCartController implements PriceObserver {

    //il carrello verrà gestito in maniera "volatile" ---> mantengo una lista in RAM delle carte aggiunte al carrello
    //quando utente la logout resetto la lista

    private CartObserver guiObserver;

    public boolean addToCart(CollectableCardBean selectedCardBean) {

        Card selectedCard = new Card(selectedCardBean.getNomeCarta(), selectedCardBean.getPrezzoCorrente(), selectedCardBean.getGradazione(), selectedCardBean.getVenditore(), selectedCardBean.getLivello(), selectedCardBean.getAttributo(), selectedCardBean.getTipo());
        try{
            SessionManager.getInstance().addCard(selectedCard);
            selectedCard.attach(this);      //Controller osserva entità
            return true;

        } catch (BaseException e) {
            ErrorHandler.show(new OperationFailedException(e.getMessage()));
            return false;
        }

    }

    // Metodo chiamato dalla UI quando l'utente apre la pagina "Il Mio Carrello"
    public List<CollectableCardBean> getCardsFromCart() {
        // 1. Recuperiamo le Entità dalla RAM
        List<Card> entitaNelCarrello = SessionManager.getInstance().getShoppingCart();

        // 2. MAPPING INVERSO: Da Entità a Bean per la UI
        List<CollectableCardBean> beansDaRestituire = new ArrayList<>();

        for (Card entita : entitaNelCarrello) {
            CollectableCardBean bean = new CollectableCardBean();
            bean.setNomeCarta(entita.getNome());
            bean.setPrezzoCorrente(entita.getPrezzoAttuale());
            bean.setVenditore(entita.getVenditore());
            bean.setLivello(entita.getLivello());
            bean.setAttributo(entita.getAttributo());
            bean.setTipo(entita.getTipo());
            bean.setGradazione(entita.getGradazione());

            beansDaRestituire.add(bean);
        }

        return beansDaRestituire;
    }


    public boolean removeFromCart(CollectableCardBean cartaBean) {
        try {
            // Creiamo un'entità "fantoccio" solo con i dati necessari per riconoscerla
            Card cartaDaRimuovere = new Card(cartaBean.getNomeCarta(), cartaBean.getPrezzoCorrente(),cartaBean.getGradazione(), cartaBean.getVenditore(), cartaBean.getLivello(), cartaBean.getAttributo(),cartaBean.getTipo());

            SessionManager.getInstance().removeCard(cartaDaRimuovere);
            return true;
        } catch (BaseException e) {
            ErrorHandler.show(new OperationFailedException(e.getMessage()));
            return false;
        }

    }

    // Metodo bonus per calcolare il totale!
    public float calcolaTotaleCarrello() {
        float totale = 0.0f;
        for (Card c : SessionManager.getInstance().getShoppingCart()) {
            totale += c.getPrezzoAttuale();
        }
        return totale;
    }

    // Metodo chiamato dalla grafica nell'initialize
    public void setCartObserver(CartObserver observer) {
        this.guiObserver = observer;
    }

    @Override
    public void updatePrice(Card modifiedCard) {
// Se c'è una schermata del carrello aperta in questo momento, la avvisiamo!
        if (guiObserver != null) {
            guiObserver.onCartUpdated();
        }
    }
}
