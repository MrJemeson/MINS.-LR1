package service.impl;

import exception.NoOrdersOnUserException;
import exception.NoSuchBookException;
import exception.NoSuchUserException;
import object.Book;
import object.Order;
import object.User;
import service.*;

import java.util.List;
import java.util.Optional;

public class UIServiceImpl implements UIService {
    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;
    private final InputService inputService;
    private final OutputService outputService;

    public UIServiceImpl(OrderService orderService, UserService userService, BookService bookService, InputService inputService, OutputService outputService) {
        this.orderService = orderService;
        this.userService = userService;
        this.bookService = bookService;
        this.inputService = inputService;
        this.outputService = outputService;
    }

    @Override
    public void mainMenu() {
        int intInput;
        while(true) {
            outputService.displayMainMenu();
            intInput = inputService.inputNumber();
            switch(intInput){
                case 1: {
                    createOrder();
                }
                case 2: {
                    closeOrder();
                }
                case 3: {
                    reportOpenOrders();
                }
            }
        }
    }

    private User findingUser(){
        String userName;
        User user;
        while (true) {
            outputService.displayUserNameInputMessage();
            userName = inputService.inputString();
            try {
                user = userService.getUserByName(userName);
                break;
            } catch (NoSuchUserException e) {
                outputService.displayExceptionMessage(e.getMessage());
            }
        }
        return user;
    }

    private Book findingBook(){
        String searchString;
        List<Book> openBooks;
        int bookNum;
        Book book;
        while (true) {
            outputService.displayBookSearchInputMessage();
            searchString = inputService.inputString();
            try {
                openBooks = bookService.getOpenBooksByNameOrAuthor(searchString);
                break;
            } catch (NoSuchBookException e) {
                outputService.displayExceptionMessage(e.getMessage());
            }
        }
        outputService.displayList(openBooks);
        while(true){
            outputService.displayPositionNumber();
            bookNum = inputService.inputNumber();
            if (bookNum <= openBooks.size()) {
                book = openBooks.get(bookNum-1);
                break;
            } else outputService.displayWrongInputMessage();
        }
        return book;
    }

    private Optional<Order> choosingOrderForUser(User user) {
        List<Order> orders;
        int orderNum;
        Order order;
        try {
            orders = orderService.getAllOrdersByUserName(user.getUserName());
        } catch (NoOrdersOnUserException e) {
            outputService.displayExceptionMessage(e.getMessage());
            return Optional.empty();
        }
        orders = orders.stream().filter(x -> !x.isClosedStatus()).toList();
        outputService.displayList(orders);
        while(true){
            outputService.displayPositionNumber();
            orderNum = inputService.inputNumber();
            if (orderNum <= orders.size()) {
                order = orders.get(orderNum-1);
                break;
            } else outputService.displayWrongInputMessage();
        }
        return Optional.of(order);
    }

    private void createOrder() {
        User user = findingUser();
        Book book = findingBook();
        orderService.createOrder(user.getId(), book.getId());
        bookService.takeBook(book.getId());
    }

    private void closeOrder() {
        User user = findingUser();
        Optional<Order> order = choosingOrderForUser(user);
        if (order.isEmpty()) {
            return;
        }
        orderService.closeOrder(order.get().getId());
        bookService.returnBook(order.get().getBookId());
    }

    private void reportOpenOrders(){
        List<Order> orders;
        try {
            orders = orderService.getAllOrders();
        } catch(NoOrdersOnUserException e) {
            outputService.displayExceptionMessage(e.getMessage());
            return;
        }
        for (int i = 0; i < orders.size(); i++) {
            Book book = bookService.getById(orders.get(i).getBookId());
            orders.get(i).setBookName(book.getBookName());
            orders.get(i).setBookAuthor(book.getAuthorName());
            orders.get(i).setUserName(userService.getUserById(orders.get(i).getUserId()).getUserName());
        }
        outputService.displayList(orders);
    }

}
