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

        // Use a boolean flag instead of while(true)
        while (stayInMenu) {
            List<NotificationBean> unreadNotif = manageNotificationsController.getUnreadNotifications(currentUser);

            if (unreadNotif.isEmpty()) {
                System.out.println("Nessuna nuova notifica!");
                stayInMenu = false; // Exits the loop gracefully instead of breaking

            } else {
                for (int i = 0; i < unreadNotif.size(); i++) {
                    NotificationBean notif = unreadNotif.get(i);
                    System.out.printf("[%d] [%s] %s\n", (i + 1), notif.getDate(), notif.getMessage());
                }

                System.out.println("-".repeat(105));
                System.out.println("Inserisci il numero della notifica per segnarla come letta");
                System.out.println("Altrimenti inserisci 0 per ignorare");
                System.out.print("Scelta -> ");
                int chosenIndex = scanner.nextInt();

                // Handle all input cases with a clean if-else chain
                if (chosenIndex == 0) {
                    stayInMenu = false; // Exits the loop gracefully

                } else if (chosenIndex > 0 && chosenIndex <= unreadNotif.size()) {
                    // The valid execution path
                    try {
                        NotificationBean selectedNotif = unreadNotif.get(chosenIndex - 1);
                        manageNotificationsController.markAsRead(selectedNotif.getId());
                        System.out.println("\nNotifica rimossa!");
                    } catch (BaseException e) {
                        ErrorHandler.show(new OperationFailedException(e.getMessage()));
                    }

                } else {
                    // Replaces the "continue" by naturally reaching the end of the loop body
                    System.out.println("\n !Scelta non valida. Inserisci un numero presente in lista.");
                }
            }
        }
    }
}
