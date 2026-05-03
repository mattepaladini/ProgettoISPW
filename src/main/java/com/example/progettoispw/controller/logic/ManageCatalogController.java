package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.utility.session.SessionManager;
import com.example.progettoispw.utility.CardMapper;

import java.util.ArrayList;
import java.util.List;

public class ManageCatalogController {

    private final ManageNotificationsController manageNotificationsController;

    public ManageCatalogController(ManageNotificationsController manageNotificationsController) {
        this.manageNotificationsController = manageNotificationsController;
    }

    public List<CollectableCardBean> getSellerCards(UserBean sellerBean) {

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        Seller sellertemp = new Seller(sellerBean.getUsername(), sellerBean.getPassword());

        CardCatalog cat = dao.getCatalogBySeller(sellertemp);

        if (cat == null) {
            return new ArrayList<>(); // Ritorna lista vuota se non ha catalogo
        }

        //popola la lista di bean da ritornare al controller grafico
        return cat.getCards().stream().map(CardMapper::toBean).toList();
    }

    public void addCard(CollectableCardBean cardBean, String seller){

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        //primo filtro sulle informazioni essenziali
        if(!cardBean.getName().isBlank() &&
            cardBean.getPrice()>0.0f){

            if(dao.findCardBySeller(cardBean.getName(), cardBean.getSeller())){
                throw new OperationFailedException("Attenzione carta presente in questo catalogo!");
            }

            Gradation gradazionetemp = Gradation.fromString(cardBean.getGradation().toString());
            Type tipotemp = Type.fromString(cardBean.getType().toString());
            Attribute attributotemp = Attribute.fromString(cardBean.getAttribute().toString());

            //ora controllo se i valori inseriti corrispondono con i valori delle ENUM

            if(gradazionetemp!=null && tipotemp!=null && attributotemp!=null){

                //i valori inseriti sono corretti quindi istanzio il nuovo oggetto
                Card newCard = new Card(cardBean.getName(), cardBean.getPrice(), cardBean.getGradation(), seller,cardBean.getLevel(), cardBean.getAttribute(), cardBean.getType());
                dao.addCard(newCard, seller);

                manageNotificationsController.publishNotification(seller, "Il venditore "+seller+" ha aggiunto una nuova carta: "+cardBean.getName());
            }
        } else {
            throw new OperationFailedException("Dati inseriti sbagliati o mancanti");
        }

    }

    public void updateCardPrice(CollectableCardBean selectedCard, Float newPrice) {

        User currentUser = SessionManager.getInstance().getLoggedUser();

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        if(!selectedCard.getName().isBlank() && newPrice>0.0f){

            dao.updatePrice(selectedCard.getName(), currentUser.getUsername(), newPrice );

            manageNotificationsController.publishNotification(currentUser.getUsername(), "Il venditore "+currentUser+" ha aggiornato il prezzo della carta "+selectedCard.getName()+" a "+ newPrice);

        }
    }

    public void removeCardFromCatalog(CollectableCardBean selectedCardBean, String seller){

        if(selectedCardBean.getName().isBlank()){
            throw new OperationFailedException("Nome carta da rimuovere mancante");
        }

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();
        Card selectedCard = CardMapper.toEntity(selectedCardBean);
        dao.removeCard(selectedCard, seller);

    }

}
