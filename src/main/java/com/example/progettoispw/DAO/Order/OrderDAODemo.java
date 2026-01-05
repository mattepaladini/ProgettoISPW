package com.example.progettoispw.DAO.Order;

import model.Order;
import model.User;

import java.util.ArrayList;
import java.util.List;

public class OrderDAODemo implements OrderDAO {

    private static List<Order> orders = new ArrayList<Order>();

    @Override
    public List<Order> getOrdersByUser(User user) {
        for (Order order : orders) {
            if(order.getCompratore().equals(user)){
                return orders;
            }
        }
        return null;
    }

    @Override
    public void executeOrder(Order order) {
        //TODO
    }
}
