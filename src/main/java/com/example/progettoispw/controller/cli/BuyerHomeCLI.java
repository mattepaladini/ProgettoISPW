package com.example.progettoispw.controller.cli;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BuyerHomeCLI {

    private Scanner scanner = new Scanner(System.in);

    Logger logger = Logger.getLogger(BuyerHomeCLI.class.getName());

    public void startBuyerHome(){

        boolean back=false;

        while(!back){

            System.out.println("-".repeat(105));
            System.out.println("--> HomePage Compratore <--");
            System.out.println("1. Visualizza Carrello");
            System.out.println("2. Cerca Carta");
            System.out.println("3. Centro Notifiche");
            System.out.println("0. Torna Indietro");
            System.out.println("-".repeat(105));

            System.out.print("Scelta --> ");
            int choice = scanner.nextInt();

            switch (choice){
                case 1:
                    CartCLI cartCLI = new CartCLI();
                    cartCLI.startCLI();
                    break;

                    case 2:
                        SearchCLI searchCLI = new SearchCLI();
                        searchCLI.startCLI();
                        break;

                        case 3:
                            FollowCLI followCLI = new FollowCLI();
                            followCLI.start();
                            break;

                        case 0:
                            back=true;
                            break;

                            default:
                                logger.log(Level.INFO, "Inserisci scelta valida");
            }
        }
    }
}
