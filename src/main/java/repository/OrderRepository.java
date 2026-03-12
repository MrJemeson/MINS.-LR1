package repository;

import object.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository {
    Optional<List<Order>> findByUserName(String userName);
    void addOrder(Order order);
    int findLastOrderId();
    void closeOrder(int orderId);
    Optional<List<Order>> getAllOrders();
}
