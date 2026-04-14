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

        while (true) {
            List<NotificationBean> unreadNotif = manageNotificationsController.getUnreadNotifications(currentUser);

            // 1. Guard Clause: se non ci sono notifiche, esco subito dal ciclo.
            if (unreadNotif.isEmpty()) {
                System.out.println("Nessuna nuova notifica!");
                break;
            }

            for (int i = 0; i < unreadNotif.size(); i++) {
                NotificationBean notif = unreadNotif.get(i);
                System.out.printf("[%d] [%s] %s\n", (i + 1), notif.getDate(), notif.getMessage());
            }

            System.out.println("-".repeat(105));
            System.out.println("Inserisci il numero della notifica per segnarla come letta");
            System.out.println("Altrimenti inserisci 0 per ignorare");
            System.out.print("Scelta -> ");
            int chosenIndex = scanner.nextInt();

            // 2. Early Exit: se preme 0, interrompo il loop immediatamente.
            if (chosenIndex == 0) {
                break;
            }

            // 3. Guard Clause sull'input invalido: se il numero è sballato, avviso e ricomincio il loop.
            if (chosenIndex < 0 || chosenIndex > unreadNotif.size()) {
                System.out.println("\n !Scelta non valida. Inserisci un numero presente in lista.");
                continue;
            }

            // 4. Esecuzione pulita: il blocco try-catch ora gestisce solo il "caso felice", senza if-else annidati!
            try {
                NotificationBean selectedNotif = unreadNotif.get(chosenIndex - 1);
                manageNotificationsController.markAsRead(selectedNotif.getId());
                System.out.println("\nNotifica rimossa!");
            } catch (BaseException e) {
                ErrorHandler.show(new OperationFailedException(e.getMessage()));
            }
        }
    }

}
