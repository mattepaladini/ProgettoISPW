package com.example.progettoispw.Controller.Logic;

import com.example.progettoispw.DAO.User.UserDAO;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.Buyer;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.Seller;


public class RegistrationController {


    public void completeRegistration(UserBean userbean) {
        UserDAO userdao = DAOFactory.getInstance().getUserDAO();

        User newUser = new User(userbean.getUsername(), userbean.getPassword(), userbean.getUsertype());

        //System.out.println(userbean.getUsertype().toString());

        userdao.addUser(newUser);
        SessionManager session = SessionManager.getInstance();

        switch (userbean.getUsertype()){
            case SELLER:
                Seller newSeller = new Seller(userbean.getUsername(), userbean.getPassword());
                session.setLoggedUser(newSeller);
                break;

                case BUYER:
                    Buyer newCustomer = new Buyer(userbean.getUsername(), userbean.getPassword());
                    session.setLoggedUser(newCustomer);
                    break;
        }



        //session.setLoggedUser(newUser);
    }
}
