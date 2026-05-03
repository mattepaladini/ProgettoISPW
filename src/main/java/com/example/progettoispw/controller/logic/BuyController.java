package com.example.progettoispw.controller.logic;

import com.example.progettoispw.bean.CollectableCardBean;
import com.example.progettoispw.bean.OrderBean;
import com.example.progettoispw.dao.cardcatalog.CardCatalogDAO;
import com.example.progettoispw.exception.InvalidInputException;
import com.example.progettoispw.exception.InvalidInputMessages;
import com.example.progettoispw.exception.OperationFailedException;
import com.example.progettoispw.model.Card;
import com.example.progettoispw.model.Order;
import com.example.progettoispw.pattern.abstractfactory.DAOFactory;
import com.example.progettoispw.pattern.decorator.*;
import com.example.progettoispw.utility.CardMapper;
import com.example.progettoispw.utility.session.SessionManager;

import java.time.LocalDate;
import java.util.List;

public class BuyController {

    public List<CollectableCardBean> searchCards(CollectableCardBean searchBean){

        //DECORATOR *****************++

        String nome = "";
        if(searchBean.getName().isBlank())
        {
            throw new InvalidInputException(InvalidInputMessages.SEARCH_CARD_FAIL);
        } else{
            nome = searchBean.getName();
        }

        SearchComponent searchStack = new BaseSearch(nome);

        if(searchBean.getPrice()>0.0f){
            searchStack = new MaxPriceFilter(searchStack, searchBean.getPrice());
        }

        if(searchBean.getAttribute()!=null){
            searchStack = new AttributeFilter(searchStack, searchBean.getAttribute());
        }

        if(searchBean.getType()!=null){
            searchStack = new TypeFilter(searchStack, searchBean.getType());
        }

        if(searchBean.getLevel()>0){
            searchStack = new LevelFilter(searchStack, searchBean.getLevel());
        }

        if(searchBean.getGradation()!=null){
            searchStack = new GradationFilter(searchStack, searchBean.getGradation());
        }

        //DECORATOR *****************++

        List<Card> carteTrovate = searchStack.executeSearch();

        //MAPPING usando la classe utility CardMapper
        return carteTrovate.stream().map(CardMapper::toBean).toList();

    }

    public OrderBean compileOrder(OrderBean orderBean, String loggedUser){

        validateCart();

        validateOrderFields(orderBean);

        List<Card> cart = SessionManager.getInstance().getShoppingCart();

        float totale = calculateTotal(cart);

        String orderData = LocalDate.now().toString();

        Order newOrder = createAndSaveOrder(orderBean, loggedUser, cart, totale, orderData);

        System.out.println("Salvataggio effettuato");

        removeCardsFromCatalogs(cart);

        SessionManager.getInstance().clearShoppingCart();

        return buildBean(orderBean, newOrder, cart, totale, orderData);

    }


    private void validateCart(){
        List<Card> cart = SessionManager.getInstance().getShoppingCart();
        if(cart == null || cart.isEmpty()){
            throw new OperationFailedException("Carrello vuoto, impossibile proseguire");
        }
    }

    private void validateOrderFields(OrderBean orderBean){
        if(orderBean.getNameSurname().isEmpty() || orderBean.getCityName().isEmpty() ||
                orderBean.getShippingAddress().isBlank() || orderBean.getPaymentCard().isBlank()
                || orderBean.getCvv().isBlank()){
            throw new OperationFailedException("Campi obbligatori mancanti");
        }
    }

    private float calculateTotal(List<Card> cart){
        float total = 0;
        for(Card card : cart){
            total += card.getPrice();
        }
        return total;
    }

    private Order createAndSaveOrder(OrderBean orderBean, String loggedUser, List<Card> cart, float totale, String orderData){
        Order newOrder = new Order(
                cart,
                orderBean.getShippingAddress(),
                loggedUser,
                totale,
                orderData
        );

        DAOFactory.getInstance().getOrderDAO().saveOrder(newOrder);
        return newOrder;
    }

    private void removeCardsFromCatalogs(List<Card> cart){
        CardCatalogDAO catalogDAO = DAOFactory.getInstance().getCardCatalogDAO();
        for(Card selledCard : cart){
            String sellerName = selledCard.getSeller();

            catalogDAO.removeCard(selledCard, sellerName);
        }
    }

    private OrderBean buildBean(OrderBean orderBean, Order newOrder, List<Card> cart, float totale, String orderData){
        orderBean.setOrderId(newOrder.getId());
        orderBean.setTotale(totale);
        orderBean.setPurchaseDate(orderData);

        orderBean.setCards(cart.stream().map(CardMapper::toBean).toList());

        String paymentCard =  orderBean.getPaymentCard();
        if (paymentCard != null && paymentCard.length() >= 4) {
            String ultimeQuattro = paymentCard.substring(paymentCard.length() - 4);
            orderBean.setPaymentCard("**** **** **** " + ultimeQuattro);
        }

        return orderBean;
    }
}
