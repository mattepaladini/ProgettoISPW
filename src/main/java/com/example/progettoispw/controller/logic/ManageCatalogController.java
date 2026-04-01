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
        if(!cardBean.getNomeCarta().isBlank() &&
            cardBean.getPrezzoCorrente()>0.0f){

            if(dao.findCardBySeller(cardBean.getNomeCarta(), cardBean.getVenditore())){
                throw new OperationFailedException("Attenzione carta presente in questo catalogo!");
            }

            Gradazione gradazionetemp = Gradazione.fromString(cardBean.getGradazione().toString());
            Type tipotemp = Type.fromString(cardBean.getTipo().toString());
            Attribute attributotemp = Attribute.fromString(cardBean.getAttributo().toString());

            //ora controllo se i valori inseriti corrispondono con i valori delle ENUM

            if(gradazionetemp!=null && tipotemp!=null && attributotemp!=null){

                //i valori inseriti sono corretti quindi istanzio il nuovo oggetto
                Card newCard = new Card(cardBean.getNomeCarta(), cardBean.getPrezzoCorrente(), cardBean.getGradazione(), seller,cardBean.getLivello(), cardBean.getAttributo(), cardBean.getTipo());
                dao.addCard(newCard, seller);
            }
        } else {
            throw new OperationFailedException("Dati inseriti sbagliati o mancanti");
        }

    }

    public void updateCardPrice(CollectableCardBean selectedCard, Float newPrice) {

        User currentUser = SessionManager.getInstance().getLoggedUser();

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        if(!selectedCard.getNomeCarta().isBlank() && newPrice>0.0f){

            dao.updatePrice(selectedCard.getNomeCarta(), currentUser.getUsername(), newPrice );
        }
    }

    public void removeCardFromCatalog(CollectableCardBean selectedCardBean, String seller){

        if(selectedCardBean.getNomeCarta().isBlank()){
            throw new OperationFailedException("Nome carta da rimuovere mancante");
        }

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();
        Card selectedCard = CardMapper.toEntity(selectedCardBean);
        dao.removeCard(selectedCard, seller);

    }

}
