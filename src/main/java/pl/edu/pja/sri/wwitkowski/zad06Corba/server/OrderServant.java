package pl.edu.pja.sri.wwitkowski.zad06Corba.server;

import pl.edu.pja.sri.wwitkowski.zad06_corba_order.Order;
import pl.edu.pja.sri.wwitkowski.zad06_corba_order.OrderSystemPOA;

import java.util.ArrayList;
import java.util.List;

public class OrderServant extends OrderSystemPOA {
    private List<Order> orderList = new ArrayList<>();
    @Override
    public void addOrder(Order order) {
        orderList.add(order);
        System.out.println("Dodano nowe zamówienie. ID: " + order.orderId);
    }

    @Override
    public void removeOrder(int orderId) {
        Order orderToRemove = null;
        for (Order order : orderList) {
            if (order.orderId == orderId) {
                orderToRemove = order;
                break;
            }
        }
        if (orderToRemove != null) {
            orderList.remove(orderToRemove);
            System.out.println("Usunięto zamówienie o ID: " + orderId);
        } else {
            System.out.println("Zamówienie o ID " + orderId + " nie istnieje.");
        }
    }



    @Override
    public Order getOrderByID(int orderId) {
        for (Order order : orderList) {
            if (order.orderId == orderId) {
                return order;
            }
        }
        return null;
    }
}
