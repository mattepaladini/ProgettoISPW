package com.example.progettoispw.controller.cli;

import com.example.progettoispw.bean.NotificationBean;
import com.example.progettoispw.controller.graphic.ErrorHandler;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.BaseException;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.List;
import java.util.Scanner;

public class FollowCLI {

    private ManageNotificationsController manageNotificationsController;
    private final Scanner scanner = new Scanner(System.in);

    public void start(){
        manageNotificationsController = new ManageNotificationsController();

        showNotif();



    }

    public void showNotif() {
        String currentUser = SessionManager.getInstance().getLoggedUser().getUsername();

        System.out.println("-".repeat(105));
        System.out.println("-> Centro Notifiche <-");

        boolean stayInMenu = true;

        while (stayInMenu) {
            List<NotificationBean> unreadNotif = manageNotificationsController.getUnreadNotifications(currentUser);

            if (unreadNotif.isEmpty()) {
                System.out.println("Nessuna nuova notifica!");
                stayInMenu = false;
            } else {
                printMenuAndNotifications(unreadNotif);
                int chosenIndex = scanner.nextInt();

                // Deleghiamo la logica complessa a un metodo esterno
                stayInMenu = processUserChoice(chosenIndex, unreadNotif);
            }
        }
    }

    // 1. ESTRAZIONE: Gestisce solo la stampa a schermo (Cognitive Complexity: 1)
    private void printMenuAndNotifications(List<NotificationBean> unreadNotif) {
        for (int i = 0; i < unreadNotif.size(); i++) {
            NotificationBean notif = unreadNotif.get(i);
            System.out.printf("[%d] [%s] %s %n", (i + 1), notif.getDate(), notif.getMessage());
        }

        System.out.println("-".repeat(105));
        System.out.println("Inserisci il numero della notifica per segnarla come letta");
        System.out.println("Altrimenti inserisci 0 per ignorare");
        System.out.print("Scelta -> ");
    }

    // 2. ESTRAZIONE: Gestisce solo l'input e l'esecuzione (Cognitive Complexity: 4)
    private boolean processUserChoice(int chosenIndex, List<NotificationBean> unreadNotif) {
        if (chosenIndex == 0) {
            return false; // Restituisce false per impostare stayInMenu = false ed uscire
        }

        if (chosenIndex < 0 || chosenIndex > unreadNotif.size()) {
            System.out.println("\n !Scelta non valida. Inserisci un numero presente in lista.");
            return true; // Restituisce true per far continuare il ciclo
        }

        // Il blocco try-catch ora è "piatto", senza if/else attorno ad esso!
        try {
            NotificationBean selectedNotif = unreadNotif.get(chosenIndex - 1);
            manageNotificationsController.markAsRead(selectedNotif.getId());
            System.out.println("\nNotifica rimossa!");
        } catch (BaseException e) {
            ErrorHandler.show(new OperationFailedException(e.getMessage()));
        }

        return true; // Restituisce true per far continuare il ciclo
    }
}
