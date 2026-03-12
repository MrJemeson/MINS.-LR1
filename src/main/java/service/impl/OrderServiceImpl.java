package service.impl;

import exception.NoOrdersOnUserException;
import object.Order;
import repository.BookRepository;
import repository.OrderRepository;
import repository.UserRepository;
import service.OrderService;

import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private BookRepository bookRepository;
    private UserRepository userRepository;

    @Override
    public void createOrder(int userId, int bookId) {
        orderRepository.addOrder(new Order(orderRepository.findLastOrderId(), userId, bookId));
    }

    @Override
    public void closeOrder(int orderId) {
        orderRepository.closeOrder(orderId);
    }

    @Override
    public List<Order> getAllOrdersByUserName(String userName) {
        return orderRepository.findByUserName(userName).orElseThrow(() -> new NoOrdersOnUserException("No orders for User " + userName + " found"));
    }

    @Override
    public List<Order> getAllOrders() {
        List<Order> orders = orderRepository.getAllOrders().orElseThrow(() -> new NoOrdersOnUserException("No orders exist"));
        orders = orders.stream().filter(x -> !x.isClosedStatus()).toList();
        if (orders.isEmpty()) throw new NoOrdersOnUserException("No open orders exist");
        return orders;
    }
}
