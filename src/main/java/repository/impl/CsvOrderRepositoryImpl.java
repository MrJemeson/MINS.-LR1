package repository.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import object.Book;
import object.Order;
import repository.OrderRepository;

import java.io.*;
import java.util.*;

public class CsvOrderRepositoryImpl implements OrderRepository {
    private final String csvHeader = "\"ID\",\"UserId\",\"BookId\",\"ClosedStatus\"";

    private List<Order> loadOrders(){
        List<Order> orders = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("data/orders.csv"))) {
            String[] parts;
            boolean firstLine = true;

            while ((parts = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                try {
                    orders.add(parseCsvLine(parts));
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", parts));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return orders;
    }

    private void saveOrders(List<Order> orders) {
        try {
            File file = new File("data/orders.csv");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                writer.println(csvHeader);
                for (Order order : orders) {
                    writer.println(toCsvLine(order));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }

    public Order parseCsvLine(String[] parts) {
        if (parts.length !=4) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        int id = Integer.parseInt(parts[0].trim());
        int userId = Integer.parseInt(parts[1].trim());
        int bookId  = Integer.parseInt(parts[2].trim());
        boolean closedStatus = (parts[3].trim().equals("true"));
        return new Order(id, bookId, userId, closedStatus);
    }

    public String toCsvLine(Order order) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(Integer.toString(order.getId()))
                .add(Integer.toString(order.getUserId()))
                .add(Integer.toString(order.getBookId()))
                .add((order.isClosedStatus())?("true"):("false"));
        return joiner.toString();
    }

    @Override
    public Optional<List<Order>> findByUserId(int userId) {
        List<Order> orders = loadOrders();
        orders = orders.stream().filter(x -> x.getUserId() == userId).toList();
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orders);
    }

    @Override
    public void addOrder(Order order) {
        List<Order> orders = loadOrders();
        orders.add(order);
        saveOrders(orders);
    }

    @Override
    public int findLastOrderId() {
        List<Order> orders = loadOrders();
        return orders.stream().max(Comparator.comparingInt(Order::getId)).map(Order::getId).orElse(0);

    }

    @Override
    public void closeOrder(int orderId) {
        List<Order> orders = loadOrders();
        Order order = orders.stream().filter(x -> x.getId() == orderId).toList().getFirst();
        order.setClosedStatus(true);
        saveOrders(orders);
    }

    @Override
    public Optional<List<Order>> getAllOrders() {
        List<Order> orders = loadOrders();
        if (orders.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(orders);
    }
}
