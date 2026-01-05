package com.example.progettoispw.DAO.Order;

import model.Order;
import model.User;

import java.util.List;

public interface OrderDAO {

    public List<Order> getOrdersByUser(User user);
    public void executeOrder(Order order);
}
