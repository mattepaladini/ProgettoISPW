package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.session.SessionManager;


public class AuthController {

    //private UserType type;

    public void checkUserExist(UserBean user) {
            //CHIAMA DAO
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        if(!user.getUsername().isBlank() && !user.getPassword().isBlank()) {
            User usertemp = userDAO.getUserByUsername(user.getUsername());
            if(usertemp!=null){
                authUser(usertemp);
            } else {
                throw new OperationFailedException("Utente non esistente");
            }
        }
    }

    public void authUser(User user) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if(userDAO.logWithPSW(user.getUsername(), user.getPassword())){
            SessionManager.getInstance().setLoggedUser(user);
        }

    }
}
