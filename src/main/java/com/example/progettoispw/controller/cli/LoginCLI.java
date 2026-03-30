package com.example.progettoispw.controller.cli;

import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.AuthController;
import com.example.progettoispw.controller.logic.RegistrationController;
import com.example.progettoispw.model.User;
import com.example.progettoispw.model.UserType;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LoginCLI {

    private final Scanner scanner = new Scanner(System.in);

    private static final Logger logger = Logger.getLogger(LoginCLI.class.getName());

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
                            startRegistration();
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

    public void startRegistration(){

        System.out.println("-".repeat(105));
        System.out.println("--> Registrazione ");

        scanner.nextLine();
        System.out.print("Username-> ");
        String username = scanner.nextLine();

        System.out.print("Password-> ");
        String password = scanner.nextLine();

        System.out.println("Sei un Venditore?   [SI/NO]");
        String isVenditore = scanner.nextLine().toLowerCase();

        UserBean userBean = new UserBean(username, password);
        if(isVenditore.equals("si")){
            userBean.setUsertype(UserType.SELLER);
        } else{
            userBean.setUsertype(UserType.BUYER);
        }
        executeRegistration(userBean);

    }

    private void executeRegistration(UserBean newUserBean){

        RegistrationController regiController = new RegistrationController();
        regiController.completeRegistration(newUserBean);

    }

    private void executeLogin(String username, String password){

        System.out.println("-".repeat(105));
        System.out.print("[Autenticazione in Corso] ");

            UserBean userBean = new UserBean(username, password);

            AuthController authController = new AuthController();
            authController.checkUserExist(userBean);

            logger.log(Level.INFO, "User {0} loggato " ,username);

            User user = SessionManager.getInstance().getLoggedUser();

            if(user.getTipoUtente().equals(UserType.BUYER)){
                BuyerHomeCLI buyerHomeCLI = new BuyerHomeCLI();
                buyerHomeCLI.startBuyerHome();
            } else if (user.getTipoUtente().equals(UserType.SELLER)) {
                SellerHomeCLI sellerHomeCLI = new SellerHomeCLI();
                sellerHomeCLI.startSellerHome();
            }

    }
}
