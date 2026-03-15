package com.example.progettoispw.dao.order;

import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.util.ArrayList;
import java.util.List;

public class OrderDAODemo implements OrderDAO {

    protected List<Order> orders = new ArrayList<Order>();

    @Override
    public List<Order> getOrdersByUser(User user) {
        for (Order order : orders) {
            if(order.getCompratore().equals(user.getUsername())){
                return orders;
            }
        }
        return orders;
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
    }

    public List<Order> getAllOrders() {
        // Restituiamo una copia della lista per evitare modifiche accidentali da fuori
        return new ArrayList<>(this.orders);
    }
}
