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

    public void showNotif(){


        boolean back = false;

        String currentUser= SessionManager.getInstance().getLoggedUser().getUsername();

        System.out.println("-".repeat(105));
        System.out.println("-> Centro Notifiche <-");

        while(!back){

            List<NotificationBean> unreadNotif = manageNotificationsController.getUnreadNotifications(currentUser);
            if(unreadNotif.isEmpty()){
                System.out.println("Nessuna nuova notifica!");
                back = true;
            } else{

                for(int i=0; i<unreadNotif.size(); i++){
                    NotificationBean notif = unreadNotif.get(i);
                    System.out.printf("[%d] [%s] %s\n", (i + 1), notif.getDate(), notif.getMessage());
                }

                System.out.println("-".repeat(105));
                System.out.println("Inserisci il numero della notifica per segnarla come letta");
                System.out.println("Altrimenti inserisci 0 per ignorare");
                System.out.print("Scelta -> ");
                int chossenIndex = scanner.nextInt();

                try{
                    if(chossenIndex == 0){
                        back = true;
                    } else if(chossenIndex > 0 && chossenIndex <= unreadNotif.size()){
                        NotificationBean selectedNotif = unreadNotif.get(chossenIndex-1);   //java parte da 0

                        manageNotificationsController.markAsRead(selectedNotif.getId());
                        System.out.println("\nNotifica rimossa!");
                    } else{
                        System.out.println("\n !Scelta non valida. Inserisci un numero presente in lista.");
                    }
                }catch (BaseException e){
                    ErrorHandler.show(new OperationFailedException(e.getMessage()));
                }
            }


        }


    }


}
