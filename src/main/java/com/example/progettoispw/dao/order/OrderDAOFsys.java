package com.example.progettoispw.dao.order;

import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.util.List;

public class OrderDAOFsys extends OrderDAODemo implements OrderDAO {
    @Override
    public List<Order> getOrdersByUser(User user) {
        return List.of();
    }

    @Override
    public List<Order> getOrdersByID(int orderID) {
        return List.of();
    }

    @Override
    public void saveOrder(Order order) {

    }
}
