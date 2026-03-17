package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.RegistrationException;
import com.example.progettoispw.model.Buyer;
import com.example.progettoispw.model.Seller;
import com.example.progettoispw.model.User;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.session.SessionManager;

import java.util.logging.Level;
import java.util.logging.Logger;


public class RegistrationController {

    private static final Logger logger = Logger.getLogger(RegistrationController.class.getName());

    public void completeRegistration(UserBean userbean) {
        UserDAO userdao = DAOFactory.getInstance().getUserDAO();

        if(userbean.getUsername().isBlank() || userbean.getPassword().isBlank()) {
            ErrorHandler.show(new InvalidInputException("Username e/o password assente"));
        }

        User newUser = new User(userbean.getUsername(), userbean.getPassword(), userbean.getUsertype());

        try {

            userdao.addUser(newUser);
            logger.log(Level.INFO, "User {0} aggiunto", newUser.getUsername());

        } catch (BaseException e) {
            ErrorHandler.show(new RegistrationException(e.getMessage()));
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
