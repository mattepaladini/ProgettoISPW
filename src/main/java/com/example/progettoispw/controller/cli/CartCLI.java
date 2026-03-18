package com.example.progettoispw.controller.cli;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.controller.logic.ManageCartController;
import com.example.progettoispw.session.SessionManager;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CartCLI {

    public static final String CARRELLO = "--> Carrello <--";
    private final Logger log = Logger.getLogger(this.getClass().getName());

    private final Scanner scanner = new Scanner(System.in);

    private final ManageCartController manageCartController = new ManageCartController();

    private Map<Integer, CollectableCardBean> mapCart = new HashMap<>();

    public void startCLI(){

        if(SessionManager.getInstance().getLoggedUser()==null){
            log.log(Level.SEVERE, "Soltanto un SELLER può accedere a questa pagina!");
        } else {
            viewCart();
            displayChoice();
        }

    }

    public void displayChoice(){

        boolean back = false;

        while(!back){
            System.out.println("-".repeat(105));
            System.out.println(CARRELLO);
            System.out.println("0. Indietro");
            System.out.println("1. COMPLETA ACQUISTO");
            System.out.println("2. Rimuovi carta dal carrello");
            System.out.println("-".repeat(105));

            System.out.print("Scelta: ");
            int choice = scanner.nextInt();

            switch(choice){
                case 0:
                    back = true;
                    break;

                    case 1:
                        CheckOutCLI checkOutCli = new CheckOutCLI();
                        checkOutCli.startCheckOut();
                        break;

                            case 2:
                                removeCardFromCart();
                                break;

            }
        }

    }

    private void viewCart(){
        List<CollectableCardBean> cardBeanList= manageCartController.getCardsFromCart();
        displayCart(cardBeanList);
    }

    private void displayCart(List<CollectableCardBean> cardBeanList){
        if(cardBeanList.isEmpty()){
            System.out.println(" [!] Carrello vuoto");
            return;
        }

        System.out.println(CARRELLO);
        System.out.printf("%-3s | %-25s | %-8s | %-12s | %-15s%n",
                "N°", "NOME CARTA", "PREZZO", "GRADAZIONE", "VENDITORE");

        System.out.println("-".repeat(105));

        int indice = 1;
        for (CollectableCardBean bean : cardBeanList) {

            mapCart.put(indice, bean);

            String nome = troncaTesto(bean.getNomeCarta(), 25);
            String prezzo = String.format("%.2f €", bean.getPrezzoCorrente());
            String gradazione = bean.getGradazione() != null ? bean.getGradazione().toString() : "N/D";
            String venditore = troncaTesto(bean.getVenditore(), 15);

            System.out.printf("%-3d | %-25s | %-8s | %-12s | %-15s%n",
                    indice, nome, prezzo, gradazione,  venditore);

            indice++;
        }

        System.out.println("-".repeat(105) + "\n");

    }

    private void removeCardFromCart(){

        System.out.println("-".repeat(105) + "\n");
        System.out.println("--> Rimuovi Carta dal carrello");
        System.out.print("Inserisci indice della carta: ");

        int indexCard = scanner.nextInt();
        mapCart.remove(indexCard);
    }

    //Metodo di supporto per i nomi delle carte troppo lunghi
    private String troncaTesto(String testo, int lunghezzaMax) {
        if (testo == null) return "N/D";
        if (testo.length() <= lunghezzaMax) return testo;
        return testo.substring(0, lunghezzaMax - 3) + "...";
    }
}
