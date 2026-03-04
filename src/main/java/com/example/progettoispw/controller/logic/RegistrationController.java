package com.example.progettoispw.controller.logic;

import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.exception.invalidInputException;
import com.example.progettoispw.exception.registrationException;
import com.example.progettoispw.model.Buyer;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.Seller;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public void completeRegistration(UserBean userbean) {
        UserDAO userdao = DAOFactory.getInstance().getUserDAO();

        User newUser = new User(userbean.getUsername(), userbean.getPassword(), userbean.getUsertype());

        //controllo semantica della bean ricevuta
        if(newUser.getUsername().isEmpty() || newUser.getPassword().isEmpty()) {
            throw new invalidInputException("Username e/o password assente");
        }

        try {

            userdao.addUser(newUser);
            logger.log(Level.INFO, "User " + newUser.getUsername() + " aggiunto");

        } catch (registrationException e) {
            logger.log(Level.WARNING,"Errore nella creazione", e);
        }
        SessionManager session = SessionManager.getInstance();

        switch (userbean.getUsertype()){
            case SELLER:
                Seller newSeller = new Seller(userbean.getUsername(), userbean.getPassword());
                session.setLoggedUser(newSeller);
                break;

            case BUYER:
            default:
                    Buyer newCustomer = new Buyer(userbean.getUsername(), userbean.getPassword());
                    session.setLoggedUser(newCustomer);
                    break;

        }
    }
}
