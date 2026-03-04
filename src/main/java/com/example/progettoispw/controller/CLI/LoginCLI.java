package com.example.progettoispw.controller.CLI;

import com.example.progettoispw.controller.Logic.AuthController;
import com.example.progettoispw.Session.SessionManager;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCLI {

    private final Scanner scanner = new Scanner(System.in);

    public static Logger logger = Logger.getLogger(SearchCLI.class.getName());

    public void startCLI(){

            boolean back = false;

            while(!back){
                System.out.println("-".repeat(105));
                System.out.println("--> Login/Registrazione <-- ");
                System.out.println("1. Login");
                System.out.println("2. Registrazione");
                System.out.println("3. Torna Indietro ");
                System.out.println("-".repeat(105));

                System.out.print("Scelta-> ");
                int choice = scanner.nextInt();
                switch(choice){
                    case 1:
                        startLogin();
                        break;

                        case 2:
                            //TODO
                            //startRegistrazione();
                            break;

                            case 3:
                                HomeCLI home = new HomeCLI();
                                home.startHomePage();
                                break;

                                default:
                                    logger.log(Level.SEVERE, "Scelta non valida");
                                    back = true;
                                    break;
                }
            }

    }

    public void startLogin(){
        System.out.println("-".repeat(105));
        System.out.println("--> Login ");

        scanner.nextLine();

        System.out.print("Username -> ");
        String username = scanner.nextLine();

        System.out.print("Password -> ");
        String password = scanner.nextLine();
        System.out.print("\n");


        executeLogin(username, password);

    }

    private void executeLogin(String username, String password){

        System.out.println("-".repeat(105));
        System.out.print("[Autenticazione in Corso] ");

        System.out.println(username + password);

        if(!username.isBlank() && !password.isBlank()){
            UserBean userBean = new UserBean(username, password);

            AuthController authController = new AuthController();
            authController.checkUserExist(userBean);

            logger.log(Level.INFO, "User " + username + " logged in");

            User user = SessionManager.getInstance().getLoggedUser();

            if(user.getTipoUtente().equals(UserType.BUYER)){
                BuyerHomeCLI buyerHomeCLI = new BuyerHomeCLI();
                buyerHomeCLI.startBuyerHome();
            } else if (user.getTipoUtente().equals(UserType.SELLER)) {
                SellerHomeCLI sellerHomeCLI = new SellerHomeCLI();
                sellerHomeCLI.startSellerHome();
            }

        } else{
            logger.log(Level.SEVERE, "Username o psw errate");
        }



    }
}
