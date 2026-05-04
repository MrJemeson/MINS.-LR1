import repository.BookRepository;
import repository.OrderRepository;
import repository.UserRepository;
import repository.impl.CsvBookRepositoryImpl;
import repository.impl.CsvUserRepositoryImpl;
import repository.impl.CsvOrderRepositoryImpl;
import service.*;
import service.impl.*;

import java.nio.file.Path;
import storage.FileStorage;
import storage.NioFileStorage;

public class App {
    public static void main(String[] args) {
        FileStorage storage = new NioFileStorage();
        UserRepository userRepository = new CsvUserRepositoryImpl(Path.of("data/users.csv"), storage);
        BookRepository bookRepository = new CsvBookRepositoryImpl(Path.of("data/books.csv"), storage);
        OrderRepository orderRepository = new CsvOrderRepositoryImpl(Path.of("data/orders.csv"), storage);
        InputService inputService = new InputServiceImpl();
        OutputService outputService = new OutputServiceImpl();
        UserService userService = new UserServiceImpl(userRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository);
        BookService bookService = new BookServiceImpl(bookRepository);
        OrderViewService orderViewService = new OrderViewServiceImpl(orderService, userService, bookService);
        UIService uiService = new UIServiceImpl(orderService, userService, bookService, orderViewService, inputService, outputService);
        uiService.mainMenu();
    }
}
