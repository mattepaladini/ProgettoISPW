package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.dao.order.OrderDAO;
import com.example.progettoispw.exception.invalidInputException;
import com.example.progettoispw.exception.operationfailedException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;
import com.example.progettoispw.pattern.AbstractFactory.DAOFactory;
import com.example.progettoispw.pattern.Decorator.*;
import com.example.progettoispw.session.SessionManager;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BuyController {

    public List<CollectableCardBean> searchCards(CollectableCardBean searchBean){

        //DECORATOR *****************++

        String nome = "";
        if(searchBean.getNomeCarta().isBlank())
        {
            throw new invalidInputException("Errore, inserire il nome della carta da cercare");
        } else{
            nome = searchBean.getNomeCarta();
        }

        SearchComponent searchStack = new BaseSearch(nome);

        if(searchBean.getPrezzoCorrente()>0.0f){
            searchStack = new MaxPriceFilter(searchStack, searchBean.getPrezzoCorrente());
        }

        if(searchBean.getAttributo()!=null){
            searchStack = new AttributeFilter(searchStack, searchBean.getAttributo());
        }

        if(searchBean.getTipo()!=null){
            searchStack = new TypeFilter(searchStack, searchBean.getTipo());
        }

        if(searchBean.getLivello()!=0 && searchBean.getLivello()>0){
            searchStack = new MaxPriceFilter(searchStack, searchBean.getLivello());
        }

        if(searchBean.getGradazione()!=null){
            searchStack = new GradationFilter(searchStack, searchBean.getGradazione());
        }

        //DECORATOR *****************++

        List<Card> carteTrovate = searchStack.executeSearch();

        //MAPPING
        List<CollectableCardBean> risultatiBean = new ArrayList<>();

        for (Card carta : carteTrovate) {
            // Creiamo un nuovo Bean vuoto per ogni carta trovata
            CollectableCardBean bean = new CollectableCardBean();

            // "Travasiamo" i dati dall'Entità al Bean
            bean.setNomeCarta(carta.getNome());
            bean.setPrezzoCorrente(carta.getPrezzoAttuale());
            bean.setLivello(carta.getLivello());
            bean.setGradazione(carta.getGradazione());
            bean.setTipo(carta.getTipo());
            bean.setAttributo(carta.getAttributo());

            bean.setVenditore(carta.getVenditore());

            // Aggiungiamo il Bean pronto alla lista finale
            risultatiBean.add(bean);
        }

        // 5. RITORNO AL CONTROLLER GRAFICO
        return risultatiBean;

    }

    public OrderBean compileOrder(OrderBean orderBean, User loggedUser){

        List<Card> cart = SessionManager.getInstance().getShoppingCart();
        if(cart == null || cart.isEmpty()){
            throw new operationfailedException("Carrello vuoto, impossibile proseguire");
        }

        float totale = 0;
        for(Card card : cart){
            totale += card.getPrezzoAttuale();
        }

        //int orderID = (int) (Math.random()*9000)+1000;      //genero ID da 1000 a 9000
        String orderData = LocalDate.now().toString();

        Order newOrder = new Order(
                cart,
                orderBean.getShippingAddress(),
                loggedUser.getUsername(),
                totale,
                orderData
        );

        OrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
        orderDAO.saveOrder(newOrder);

        System.out.println("Salvataggio effettuato");

        //rimuovo carte dal/dai venditore/i

        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();
        for(Card selledCard : cart){
            String sellerName = selledCard.getVenditore();
            catalogDAO.removeCard(selledCard, sellerName);
        }

        //TODO non devoa anche gestire gli observer?

        //svuoto carrello
        SessionManager.getInstance().clearShoppingCart();

        orderBean.setOrderId(newOrder.getId());
        orderBean.setTotale(totale);
        orderBean.setPurchaseDate(orderData);
        //orderBean.setCards(cart);     //TODO AGGIUSTA

        String paymentCard =  orderBean.getPaymentCard();
        if (paymentCard != null && paymentCard.length() >= 4) {
            String ultimeQuattro = paymentCard.substring(paymentCard.length() - 4);
            orderBean.setPaymentCard("**** **** **** " + ultimeQuattro);
        }

        return orderBean;
    }
}
