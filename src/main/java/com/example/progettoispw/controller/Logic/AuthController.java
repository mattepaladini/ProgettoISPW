package com.example.progettoispw.controller.Logic;

import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.exception.invalidInputException;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import com.example.progettoispw.model.User;


public class AuthController {

    public UserType type;

    public void checkUserExist(UserBean user) {
            //CHIAMA DAO
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        User usertemp = userDAO.getUserByUsername(user.getUsername());
        if(usertemp!=null){
            authUser(usertemp);
        } else {
           throw new invalidInputException("Utente non esistente");
        }

    }

    public void authUser(User user) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if(userDAO.logWithPSW(user.getUsername(), user.getPassword())){
            SessionManager.getInstance().setLoggedUser(user);
        }

    }
}
