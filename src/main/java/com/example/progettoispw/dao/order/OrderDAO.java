package com.example.progettoispw.dao.order;

import com.example.progettoispw.model.Order;
import com.example.progettoispw.model.User;

import java.util.List;

public interface OrderDAO {

     List<Order> getOrdersByUser(User user);      //restituisce la lista di ordini associato all'utente
     List<Order> getOrdersByID(int orderID);      //restituisce l'ordine associato a un certo ID
     void saveOrder(Order order);            //scelta di progetto, l'ordine è fittizio quindi mi interessa solo salvarlo nel livello di persistenza
     void deleteOrder(Order order);
}
