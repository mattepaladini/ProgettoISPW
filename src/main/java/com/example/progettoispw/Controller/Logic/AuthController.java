package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import model.User;
import model.UserType;

public class AuthController {

    public UserType type;

    public void checkUserExist(UserBean user) {
            //CHIAMA DAO
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        User usertemp = new User(userBean.getUsername(), userBean.getPassword(),null);
        if(userDAO.getUserByUsername(userBean.getUsername())!=null){
            authUser(usertemp);
        } else {
           //TODO throw(TODO);
        }

    }

    public void authUser(User user) {
        UserDAO userDAO = DAOFactory.getInstance().getUserDAO();
        if(userDAO.logWithPSW(user.getPassword())){
            SessionManager.getInstance().setLoggedUser(user);
        }

    }
}
