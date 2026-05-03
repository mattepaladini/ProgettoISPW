package com.example.progettoispw.controller.cli;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.controller.logic.ManageNotificationsController;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Gradation;
import com.example.progettoispw.model.Type;
import com.example.progettoispw.model.User;
import com.example.progettoispw.utility.session.SessionManager;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SellerHomeCLI {

    Logger logger = Logger.getLogger(SellerHomeCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    private ManageCatalogController logicController;
    private final ManageNotificationsController notificationsController;

    public SellerHomeCLI(){
        this.notificationsController = new ManageNotificationsController();
        this.logicController = new ManageCatalogController(notificationsController);
    }

    public void startSellerHome(){
        
        boolean back = false;

        while(!back){

            System.out.println("-".repeat(105));
            System.out.println("--> HomePage Venditore <--");
            System.out.println("1. Visualizza Catalogo");
            System.out.println("2. Aggiungi Carta");
            System.out.println("3. Modifica Prezzo della carta");
            System.out.println("4. Centro Notifiche");
            System.out.println("0. Torna Indietro");
            System.out.println("-".repeat(105));

            System.out.print("Scelta --> ");
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    viewCatalog();
                    break;

                case 2:
                    addCard();
                    break;

                case 3:
                    updatePrice();
                    break;

                    case 4:
                        FollowCLI followCli = new FollowCLI();
                        followCli.start();
                        break;

                case 0:
                    back = true;
                    break;

                    default:
                        logger.log(Level.SEVERE, "Scelta non valida");
            }
        }

    }

    public void viewCatalog(){

        System.out.println("-".repeat(105));
        System.out.println("--> Catalogo <--");

        User currentSeller = SessionManager.getInstance().getLoggedUser();
        UserBean userBean = new UserBean(currentSeller.getUsername(), "");      //per recuperare le carte del catalogo non mi serve passare anche la password quindi metto la stringa vuota
        List<CollectableCardBean> cards = logicController.getSellerCards(userBean);

        displayCatalog(cards);

    }

    public void displayCatalog(List<CollectableCardBean> cards){

        if (cards == null || cards.isEmpty()) {
            System.out.println("\n[!] Il catalogo è attualmente vuoto.");
            return;
        }

        System.out.printf("%-3s | %-25s | %-10s | %-12s | %-12s | %-12s | %-4s%n",
                "N°", "NOME CARTA", "PREZZO", "GRADAZIONE", "TIPO", "ATTRIBUTO", "LIV");

        System.out.println("-".repeat(95));

        int indice = 1;
        for (CollectableCardBean bean : cards) {

            String nome = troncaTesto(bean.getName(), 25);
            String prezzo = String.format("%.2f €", bean.getPrice());
            String gradazione = bean.getGradation() != null ? bean.getGradation().toString() : "N/D";
            String tipo = bean.getType() != null ? bean.getType().toString() : "N/D";
            String attributo = bean.getAttribute() != null ? bean.getAttribute().toString() : "N/D";

            String livello = bean.getLevel() > 0 ? String.valueOf(bean.getLevel()) : "-";

            System.out.printf("%-3d | %-25s | %-10s | %-12s | %-12s | %-12s | %-4s%n",
                    indice, nome, prezzo, gradazione, tipo, attributo, livello);

            indice++;
        }

        System.out.println("-".repeat(95) + "\n");
    }

    public void addCard(){

        CollectableCardBean newCardBean = new CollectableCardBean();
        scanner.nextLine();
        System.out.println("-".repeat(105));
        System.out.println("--> Aggiungi Carta <--");
        System.out.print("Nome della Carta --> ");
        String nome = scanner.nextLine();
        newCardBean.setName(nome);


        System.out.print("Prezzo della Carta --> ");
        Float prezzo = Float.parseFloat(scanner.nextLine());
        newCardBean.setPrice(prezzo);

        System.out.print("Livello della Carta [0-12] --> ");
        int livello = Integer.parseInt(scanner.nextLine());
        newCardBean.setLevel(livello);

        System.out.println("Gradazioni: [PERFETTO,  BUONO,  USATO,  SCARSO]");
        System.out.print("Gradation della Carta  --> ");
        String gradazioneGrezza = scanner.nextLine();

        newCardBean.setGradation(Gradation.valueOf(gradazioneGrezza.toUpperCase()));

        System.out.println("Tipi:  [MOSTRO,  MAGIA,   TERRENO,  TRAPPOLA]");
        System.out.print("Tipo della Carta  --> ");
        String tipoGrezza = scanner.nextLine();
        newCardBean.setType(Type.valueOf(tipoGrezza.toUpperCase()));

        System.out.println("Attributi:  [LUCE,  OSCURITÀ,   TERRA,  ACQUA,  FUOCO]");
        System.out.print("Attributo della Carta  --> ");
        String attributoGrezza = scanner.nextLine();
        newCardBean.setAttribute(Attribute.valueOf(attributoGrezza.toUpperCase()));

        try{
            User currentSeller = SessionManager.getInstance().getLoggedUser();

            logicController.addCard(newCardBean,currentSeller.getUsername());
        } catch (Exception e) {
            throw new OperationFailedException(e.getMessage());
        }

        logger.log(Level.INFO,"Carta aggiunta con successo");
    }

    public void updatePrice() {

        scanner.nextLine();
        System.out.println("-".repeat(105));
        System.out.println("--> Modifica il Prezzo di una Carta <--");
        System.out.print("Nome della Carta --> ");
        String nomeCarta =  scanner.nextLine();

        System.out.print("NUOVO Prezzo della Carta --> ");
        Float prezzo = scanner.nextFloat();

        CollectableCardBean cardBean = new CollectableCardBean(nomeCarta, prezzo);

        try{
            logicController.updateCardPrice(cardBean, prezzo);
        }catch (Exception e) {
            throw new OperationFailedException(e.getMessage());
        }

    }

    //metodo per aiutare a visualizzare i dati dentro la tabella
    private String troncaTesto(String testo, int lunghezzaMax) {
        if (testo == null) return "N/D";
        if (testo.length() <= lunghezzaMax) return testo;
        return testo.substring(0, lunghezzaMax - 3) + "...";
    }
}
