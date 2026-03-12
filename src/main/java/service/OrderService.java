package service;

import object.Order;

import java.util.List;

public interface OrderService {
    void createOrder(int userId, int bookId);
    void closeOrder(int orderId);
    List<Order> getAllOrdersByUserId(int userId);
    List<Order> getAllOrders();
}
