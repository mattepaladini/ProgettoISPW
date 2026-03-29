package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.dao.user.UserDAO;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.User;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.session.SessionManager;


public class AuthController {

    public void checkUserExist(UserBean user) {

        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();

        if(!user.getUsername().isBlank() && !user.getPassword().isBlank()) {
            User usertemp = userDAO.getUserByUsername(user.getUsername());
            if(usertemp!=null){
                authUser(user);
                SessionManager.getInstance().setLoggedUser(usertemp);
            } else {
                throw new OperationFailedException("Username non trovato");
            }
        }else{
            throw new OperationFailedException("Username o passwors non inseriti");
        }
    }

    public void authUser(UserBean user) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if(!userDAO.logWithPSW(user.getUsername(), user.getPassword())){
            throw new OperationFailedException("Psw errata");
        }
    }
}
