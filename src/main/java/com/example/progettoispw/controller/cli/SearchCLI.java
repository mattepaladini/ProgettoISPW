package com.example.progettoispw.controller.cli;

import com.example.progettoispw.controller.logic.BuyController;
import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.model.Attribute;
import com.example.progettoispw.model.Type;

import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SearchCLI {

    public static final String DISABILITA_FILTRO = "0. Disabilita Filtro";
    private static final Logger logger = Logger.getLogger(SearchCLI.class.getName());

    private final Scanner scanner = new Scanner(System.in);

    CollectableCardBean cardBean = new CollectableCardBean();

    private static final String CERCA_CARTE ="--> Cerca Carte <--";
    private static final String SCELTA="Scelta-> ";

    public void startCLI(){

        boolean back = false;

        while(!back){

            System.out.println("-".repeat(105));
            System.out.println(CERCA_CARTE);
            System.out.println("0. Torna Indietro");
            System.out.println("1. Cerca per Nome della carta");
            System.out.println("2. Aggiungi filtri (Gradazione, Prezzo, Tipo, Attributo, ...)");
            System.out.println("3. ESEGUI RICERCA");
            System.out.println("-".repeat(105));

            System.out.print(SCELTA);
            int choice = scanner.nextInt();

            switch (choice){

                case 0:
                    back = true;
                    break;

                    case 1:
                        configureSearchQuery();
                        break;

                        case 2:
                            configureFilters();
                            break;

                            case 3:
                                executeQuery();
                                break;

                                default:
                                    logger.log(Level.SEVERE, "Inserire una scelta valida!");
                                    break;
            }

        }
    }


    public void configureSearchQuery(){

        System.out.println("-".repeat(105));
        System.out.println(CERCA_CARTE);
        System.out.print("Inserisci il nome della carta da ricercare --> ");
        //Scanner scanner = new Scanner(System.in);
        String nomeCarta = scanner.nextLine();

        cardBean.setNomeCarta(nomeCarta);

    }

    public void configureFilters(){

        System.out.println("-".repeat(105));
        System.out.println(CERCA_CARTE);
        System.out.println("Compila i diversi filtri con il valore specificato, altrimenti 0 se non vuoi usare il filtro:");

        System.out.println("--> Filtro Prezzo");
        System.out.println(DISABILITA_FILTRO);
        System.out.println("5. Meno di 5 €");
        System.out.println("10. Fino a 10 €");
        System.out.println("15. Fino a 15 €");
        System.out.println("20. Fino a 20 €");
        System.out.println("50. Fino a 50 €");
        System.out.println("100. Fino a 100 €");

        System.out.print(SCELTA);
        Float prezzo = scanner.nextFloat();
        cardBean.setPrezzoCorrente(prezzo);



        System.out.println("-".repeat(105));
        System.out.println("--> Filtro Livello:");
        System.out.println(DISABILITA_FILTRO);
        System.out.println("1-12. Inserisci il numero tra 1 e 12");

        System.out.print(SCELTA);
        scanner.nextLine();     //consumo \n
        int livello = scanner.nextInt();
        cardBean.setLivello(livello);


        System.out.println("-".repeat(105));
        System.out.println("--> Filtro Attributo");
        System.out.println(DISABILITA_FILTRO);
        System.out.println("LUCE,   OSCURITÀ,   TERRA,  ACQUA,  FUOCO");

        System.out.print(SCELTA);
        scanner.nextLine();     //consumo \n
        String attributo = scanner.nextLine().toUpperCase();
        switch (attributo){

            case "0":
                break;

            case "LUCE":
                cardBean.setAttributo(Attribute.LUCE);
                break;

                case "OSCURITÀ":
                    cardBean.setAttributo(Attribute.OSCURITA);
                    break;

                    case "TERRA":
                        cardBean.setAttributo(Attribute.TERRA);
                        break;

                        case "ACQUA":
                            cardBean.setAttributo(Attribute.ACQUA);
                            break;

                            case "FUOCO":
                                cardBean.setAttributo(Attribute.FUOCO);
                                break;

                                default:
                                    logger.log(Level.SEVERE, "Scelta non valida, valore default impostato");
                                    //valore non impostato quindi campo è NULL
                                    break;
        }



        System.out.println("-".repeat(105));
        System.out.println("--> Filtro Tipo");
        System.out.println(DISABILITA_FILTRO);
        System.out.println("MOSTRO,   MAGIA,   TERRENO,   TRAPPOLA");

        System.out.print(SCELTA);
        scanner.nextLine();
        String tipo = scanner.nextLine().toUpperCase();
        switch (tipo){

            case "0":
                break;

            case "MOSTRO":
                cardBean.setTipo(Type.MOSTRO);
                break;

            case "MAGIA":
                cardBean.setTipo(Type.MAGIA);
                break;

            case "TERRENO":
                cardBean.setTipo(Type.TERRENO);
                break;

                case "TRAPPOLA":
                    cardBean.setTipo(Type.TRAPPOLA);
                    break;

                    default:
                        logger.log(Level.SEVERE, "Scelta non valida, valore default impostata");
                        break;
        }


        scanner.nextLine();

        executeQuery();

    }

    public void executeQuery(){

        System.out.println("-".repeat(105));
        System.out.println("--> Risultati della Ricerca <--");

        BuyController buyController = new BuyController();
        List<CollectableCardBean> risultati = buyController.searchCards(cardBean);

        // 1. Controllo base: la lista è vuota?
        if (risultati == null || risultati.isEmpty()) {
            System.out.println("\n[!] Nessuna carta trovata con i filtri selezionati.");
            return;
        }

        System.out.printf("%-3s | %-25s | %-8s | %-12s | %-15s%n",
                "N°", "NOME CARTA", "PREZZO", "GRADAZIONE", "VENDITORE");

        System.out.println("-".repeat(105));

        int indice = 1;
        for (CollectableCardBean bean : risultati) {

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

    //Metodo di supporto per i nomi delle carte troppo lunghi
    private String troncaTesto(String testo, int lunghezzaMax) {
        if (testo == null) return "N/D";
        if (testo.length() <= lunghezzaMax) return testo;
        return testo.substring(0, lunghezzaMax - 3) + "...";
    }
}
