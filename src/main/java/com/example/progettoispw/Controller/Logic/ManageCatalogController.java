package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.CardCatalog.CardCatalogDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import model.User;

public class ManageCatalogController {


    public void removeCardtoCatalog(CollectableCardBean card) throws Exception{

        User currentSeller = SessionManager.getInstance().getLoggedUser();

        if(currentSeller==null){
            throw new Exception("Eseguire Login!");
        }

        String sellerName = currentSeller.getUsername();

        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();

        catalogDAO.removeCard(card, sellerName);

    }
}
