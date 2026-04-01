package com.example.progettoispw.controller.cli;

import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.Scanner;

public class CheckOutCLI {

    private Scanner scanner= new Scanner(System.in);
    private OrderBean newOrder = new OrderBean();

    public void startCheckOut() {

        System.out.println("-".repeat(105));
        System.out.println("--> Creazione Ordine <--");
        System.out.println("-".repeat(50));
        System.out.println("Dati di Spedizione");

        System.out.print("Nome e Cognome: ");
        String nomeCognome = scanner.nextLine().trim();
        newOrder.setNameSurname(nomeCognome);

        System.out.print("Citta': ");
        String citta = scanner.nextLine().trim();
        newOrder.setCityName(citta);

        System.out.print("Indirizzo di spedizione (es. Via Roma 10): ");
        String spedizione = scanner.nextLine().trim();
        newOrder.setShippingAddress(spedizione);

        System.out.println("-".repeat(50));
        System.out.println("Dati di Pagamento");
        System.out.print("Numero Carta di pagamento: ");
        String numeroCarta = scanner.nextLine().trim();
        newOrder.setPaymentCard(numeroCarta);

        System.out.print("CVV: ");
        String cvv = scanner.nextLine().trim();
        newOrder.setCvv(cvv);

        System.out.println("Vuoi procedere all'acquisto? [S/N]");
        String conferma = scanner.nextLine().trim().toUpperCase();

        if(conferma.equals("S")) {
            BuyController buyController = new BuyController();
            OrderBean orderDone =  buyController.compileOrder(newOrder, SessionManager.getInstance().getLoggedUser().getUsername());

            displayOrderOutcome(orderDone);
        }
    }

    private void displayOrderOutcome(OrderBean order) {
        System.out.println("\n" + "=".repeat(50));
        System.out.println(" ACQUISTO COMPLETATO CON SUCCESSO!");
        System.out.println("=".repeat(50));
        System.out.println("Ordine N°: " + order.getOrderId());
        System.out.println("Data: " + order.getPurchaseDate());
        System.out.printf("Totale Pagato: %.2f €%n", order.getTotale());
        System.out.println("Metodo di pagamento: " + order.getPaymentCard());
        System.out.println("Spedito a: " + order.getShippingAddress() + " (" + order.getCityName() + ")");
        System.out.println("=".repeat(50) + "\n");
    }
}
