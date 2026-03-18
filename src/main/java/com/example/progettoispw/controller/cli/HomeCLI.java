package com.example.progettoispw.controller.cli;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomeCLI {

        public void startHomePage(){

            Logger logger = Logger.getLogger(HomeCLI.class.getName());
            boolean exit = false;

            while(!exit){

                System.out.println("-".repeat(105));
                System.out.println("Benvenuto nella HomePage, cosa vuoi fare?: ");
                System.out.println("0. ESCI");
                System.out.println("1. Cerca Carte");
                System.out.println("2. Il mio Profilo");
                //System.out.println("3. Compra Carte (SOLO per compratori)");
                //System.out.println("4. Vendi Carte (SOLO per venditori)");
                System.out.println("-".repeat(105));

                System.out.print("Scelta-> ");
                Scanner scanner = new Scanner(System.in);
                int choice = scanner.nextInt();

                switch(choice){

                    case 0:
                        logger.log(Level.INFO, "Uscita dal sistema. Arrivederci.");
                        exit = true;
                        break;

                    case 1:
                        SearchCLI searchCLI = new SearchCLI();
                        searchCLI.startCLI();
                        break;

                        case 2:
                            LoginCLI loginCLI = new LoginCLI();
                            loginCLI.startCLI();
                            break;

                            case 3:
                                CartCLI cartCLI = new CartCLI();
                                cartCLI.startCLI();
                                break;

                                case 4:
                                    SellerHomeCLI sellCLI = new SellerHomeCLI();
                                    sellCLI.startSellerHome();
                                    break;

                                    default:
                                        logger.log(Level.SEVERE, "Inserire una scelta valida");
                }
            }


        }

}
