package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.*;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;

import javax.xml.catalog.Catalog;
import java.util.ArrayList;
import java.util.List;

public class ManageCatalogController {

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
}
