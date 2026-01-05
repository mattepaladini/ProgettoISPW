package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.CardCatalogDAO;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.Session;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;

public class ManageCatalogController {


    public void removeCardtoCatalog(CollectableCardBean card) throws Exception{

        UserBean currentSeller = Session.getInstance().getLoggedUser();

        if(currentSeller==null){
            throw new Exception("Eseguire Login!");
        }

        String sellerName = currentSeller.getUsername();

        CardCatalogDAO catalogDAO = DAOFactory.getCardCatalogDAO();

        catalogDAO.removeCard(card, sellerName);

    }
}
