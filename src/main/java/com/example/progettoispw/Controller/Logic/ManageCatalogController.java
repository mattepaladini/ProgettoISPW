package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Gradazione;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import com.example.progettoispw.model.User;

public class ManageCatalogController {


    public void removeCardtoCatalog(CollectableCardBean cardBean) throws Exception{

        User currentSeller = SessionManager.getInstance().getLoggedUser();

        if(currentSeller==null){
            throw new Exception("Eseguire Login!");
        }

        User seller = new User(cardBean.getVenditore());
        Gradazione gr = Gradazione.fromString(cardBean.getGradazione());

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

    }
}
