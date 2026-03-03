package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ManageCatalogController {

    Logger logger = Logger.getLogger(ManageCatalogController.class.getName());

    /*
    public void removeCardtoCatalog(CollectableCardBean cardBean) throws Exception{

        User currentSeller = SessionManager.getInstance().getLoggedUser();

        if(currentSeller==null){
            throw new Exception("Eseguire Login!");
        }

        User seller = new User(cardBean.getVenditore());
        Gradazione gr = cardBean.getGradazione();

        Card card = new Card(
                cardBean.getNomeCarta(),
                cardBean.getPrezzoCorrente(),
                gr,
                seller,
                cardBean.getId()
        );

        String sellerName = currentSeller.getUsername();

        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();

        catalogDAO.removeCard(card, sellerName);

    }*/

    public List<CollectableCardBean> getSellerCards(UserBean sellerBean) {
        // 1. Ottengo il DAO
        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        Seller sellertemp = new Seller(sellerBean.getUsername(), sellerBean.getPassword());

        CardCatalog cat = dao.getCatalogBySeller(sellertemp);

        List<CollectableCardBean> beanList = new ArrayList<>();

        if (cat == null) {
            return new ArrayList<>(); // Ritorna lista vuota se non ha catalogo
        }

        //popola la lista di bean da ritornare al controller grafico

        for(Card card : cat.getCards()){
            CollectableCardBean bean = new CollectableCardBean();

            bean.setNomeCarta(card.getNome());
            bean.setPrezzoCorrente(card.getPrezzoAttuale());
            bean.setGradazione(card.getGradazione());
            bean.setLivello(card.getLivello());
            bean.setAttributo(card.getAttributo());
            bean.setTipo(card.getTipo());

            beanList.add(bean);
        }

        return beanList; // Restituisce la List<CardBean>
    }

    public void addCard(CollectableCardBean cardBean, User seller){

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        //primo filtro sulle informazioni essenziali
        if(!cardBean.getNomeCarta().isBlank() &&
            cardBean.getPrezzoCorrente()>0.0f){

            if(!dao.findCardBySeller(cardBean.getNomeCarta(), cardBean.getVenditore())){
                logger.log(Level.WARNING, "Attenzione carta già presente in questo catalogo!");
                return;     //forzo l'uscita
            }

            Gradazione gradazionetemp = Gradazione.fromString(cardBean.getGradazione().toString());
            Type tipotemp = Type.fromString(cardBean.getTipo().toString());
            Attribute attributotemp = Attribute.fromString(cardBean.getAttributo().toString());

            //ora controllo se i valori inseriti corrispondono con i valori delle ENUM

            if(gradazionetemp!=null && tipotemp!=null && attributotemp!=null){

                //i valori inseriti sono corretti quindi istanzio il nuovo oggetto
                Card newCard = new Card(cardBean.getNomeCarta(), cardBean.getPrezzoCorrente(), cardBean.getGradazione(), seller.getUsername(),cardBean.getLivello(), cardBean.getAttributo(), cardBean.getTipo());
                dao.addCard(newCard, seller.getUsername());
            }
        } else {
            logger.log(Level.SEVERE, "Dati inseriti sbagliati");
        }

    }

    public void updateCardPrice(CollectableCardBean selected, Float newPrice) {

        User currentUser = SessionManager.getInstance().getLoggedUser();

        CardCatalogDAO dao = DAOFactory.getInstance().getCardCatalogDAO();

        if(!selected.getNomeCarta().isBlank() && newPrice>0.0f){
            dao.updatePrice(selected.getNomeCarta(), currentUser.getUsername(), newPrice );
        }
    }


}
