package com.example.progettoispw.dao.order;

import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.util.List;

public interface OrderDAO {

    public List<Order> getOrdersByUser(User user);
    public void executeOrder(Order order);
}
