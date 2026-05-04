package service.impl;

import exception.NoOrdersOnUserException;
import object.Order;
import repository.OrderRepository;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Override
    public void createOrder(int userId, int bookId) {
        orderRepository.addOrder(new Order(orderRepository.findLastOrderId() + 1, bookId, userId));
    }

    @Override
    public void closeOrder(int orderId) {
        orderRepository.closeOrder(orderId);
    }

    @Override
    public List<Order> getAllOrdersByUserId(int userId) {
        List<Order> orders = orderRepository.findByUserId(userId).orElseThrow(() -> new NoOrdersOnUserException("No orders for User " + userId + " found"));
        orders = orders.stream().filter(x -> !x.isClosedStatus()).toList();
        if(orders.isEmpty()) {
            throw new NoOrdersOnUserException("No orders for User " + userId + " found");
        }
        return orderRepository.findByUserId(userId).orElseThrow(() -> new NoOrdersOnUserException("No orders for User " + userId + " found"));
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.getAllOrders().orElseThrow(() -> new NoOrdersOnUserException("No orders exist"));
        orders = orders.stream().filter(x -> !x.isClosedStatus()).toList();
        if (orders.isEmpty()) throw new NoOrdersOnUserException("No open orders exist");
        return orders;
    }
}
