package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.InvalidInputMessages;
import com.example.progettoispw.exception.RegistrationException;
import com.example.progettoispw.model.Buyer;
import com.example.progettoispw.model.CardCatalog;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.utility.PasswordHasher;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public void completeRegistration(UserBean userbean) {
        UserDAO userdao = DAOFactory.getInstance().getUserDAO();

        if(userbean.getUsername().isBlank() || userbean.getPassword().isBlank()) {
            throw  new InvalidInputException(InvalidInputMessages.REGISTRATION_FAIL);
        }

        String hashedPassword = PasswordHasher.hash(userbean.getPassword());
        User newUser = new User(userbean.getUsername(), hashedPassword, userbean.getUsertype());

        try {

            userdao.addUser(newUser);
            logger.log(Level.INFO, "User {0} aggiunto", newUser.getUsername());

        } catch (BaseException e) {
            throw new RegistrationException(e.getMessage());
        }

        SessionManager session = SessionManager.getInstance();

        switch (userbean.getUsertype()){
            case SELLER:
                Seller newSeller = new Seller(userbean.getUsername(), hashedPassword);
                session.setLoggedUser(newSeller);

                CardCatalog newCardCatalog = new CardCatalog(newSeller);
                CardCatalogDAO cardCatalogDAO = DAOFactory.getInstance().getCardCatalogDAO();
                cardCatalogDAO.addCatalog(newCardCatalog);
                break;

            case BUYER:
            default:
                    Buyer newCustomer = new Buyer(userbean.getUsername(), hashedPassword);
                    session.setLoggedUser(newCustomer);
                    break;

        }
    }
}
