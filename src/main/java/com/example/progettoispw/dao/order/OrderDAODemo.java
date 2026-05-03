package com.example.progettoispw.dao.order;

import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OrderDAODemo implements OrderDAO {

    protected List<Order> orders = new ArrayList<>();
    private static final Logger log = Logger.getLogger(OrderDAODemo.class.getName());

    @Override
    public List<Order> getOrdersByUser(User user) {
        return orders.stream()
                .filter(o -> o.getCompratore().equals(user.getUsername()))
                .toList();

    }

    @Override
    public List<Order> getOrdersByID(int orderID) {

        List<Order> tempOrders = new ArrayList<>();

        for(Order order : orders){
            if(order.getId() == orderID){
                tempOrders.add(order);
            }
        }

        return tempOrders;
    }

    @Override
    public void saveOrder(Order order) {
        this.orders.add(order);
        log.log(Level.INFO, "Ordine salvato.");
    }

    @Override
    public void deleteOrder(Order order) {
        for(Order tempOrder : getAllOrders()){
            if(tempOrder.getId() == order.getId()){
                orders.remove(tempOrder);
                log.log(Level.INFO, "Ordine {0}",order.getId()+" rimosso.");
                break;
            }
        }
    }


    public List<Order> getAllOrders() {
        // Restituiamo una copia della lista per evitare modifiche accidentali da fuori
        return new ArrayList<>(this.orders);
    }
}
