package com.example.progettoispw.controller.cli;

import com.example.progettoispw.controller.logic.ManageCatalogController;
import com.example.progettoispw.exception.operationfailedException;
import com.example.progettoispw.session.SessionManager;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.UserBean;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Gradazione;
import com.example.progettoispw.model.Type;
import com.example.progettoispw.model.User;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SellerHomeCLI {

    Logger logger = Logger.getLogger(SellerHomeCLI.class.getName());
    private final Scanner scanner = new Scanner(System.in);

    //


    private ManageCatalogController logicController;


    public void startSellerHome(){

        this.logicController = new ManageCatalogController();
        boolean back = false;

        while(!back){

            System.out.println("-".repeat(105));
            System.out.println("--> HomePage Venditore <--");
            System.out.println("1. Visualizza Catalogo");
            System.out.println("2. Aggiungi Carta");
            System.out.println("3. Modifica Prezzo della carta");
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
        UserBean userBean = new UserBean(currentSeller.getUsername(), currentSeller.getPassword());
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

            String nome = troncaTesto(bean.getNomeCarta(), 25);
            String prezzo = String.format("%.2f €", bean.getPrezzoCorrente());
            String gradazione = bean.getGradazione() != null ? bean.getGradazione().toString() : "N/D";
            String tipo = bean.getTipo() != null ? bean.getTipo().toString() : "N/D";
            String attributo = bean.getAttributo() != null ? bean.getAttributo().toString() : "N/D";

            String livello = bean.getLivello() > 0 ? String.valueOf(bean.getLivello()) : "-";

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
        newCardBean.setNomeCarta(nome);


        System.out.print("Prezzo della Carta --> ");
        Float prezzo = Float.parseFloat(scanner.nextLine());
        newCardBean.setPrezzoCorrente(prezzo);

        System.out.print("Livello della Carta [0-12] --> ");
        int livello = Integer.parseInt(scanner.nextLine());
        newCardBean.setLivello(livello);

        System.out.println("Gradazioni: [PERFETTO,  BUONO,  USATO,  SCARSO]");
        System.out.print("Gradazione della Carta  --> ");
        String gradazioneGrezza = scanner.nextLine();

        newCardBean.setGradazione(Gradazione.valueOf(gradazioneGrezza.toUpperCase()));

        System.out.println("Tipi:  [MOSTRO,  MAGIA,   TERRENO,  TRAPPOLA]");
        System.out.print("Tipo della Carta  --> ");
        String tipoGrezza = scanner.nextLine();
        newCardBean.setTipo(Type.valueOf(tipoGrezza.toUpperCase()));

        System.out.println("Attributi:  [LUCE,  OSCURITÀ,   TERRA,  ACQUA,  FUOCO]");
        System.out.print("Attributo della Carta  --> ");
        String attributoGrezza = scanner.nextLine();
        newCardBean.setAttributo(Attribute.valueOf(attributoGrezza.toUpperCase()));

        try{
            User currentSeller = SessionManager.getInstance().getLoggedUser();

            logicController.addCard(newCardBean,currentSeller );
        } catch (Exception e) {
            throw new operationfailedException(e.getMessage());
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
            throw new operationfailedException(e.getMessage());
        }

    }

    //metodo per aiutare a visualizzare i dati dentro la tabella
    private String troncaTesto(String testo, int lunghezzaMax) {
        if (testo == null) return "N/D";
        if (testo.length() <= lunghezzaMax) return testo;
        return testo.substring(0, lunghezzaMax - 3) + "...";
    }
}
