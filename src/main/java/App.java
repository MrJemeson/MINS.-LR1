import repository.BookRepository;
import repository.OrderRepository;
import repository.UserRepository;
import repository.impl.CsvBookRepositoryImpl;
import repository.impl.CsvUserRepositoryImpl;
import repository.impl.CsvOrderRepositoryImpl;
import service.*;
import service.impl.*;

public class App {
    public static void main(String[] args) {
        UserRepository userRepository = new CsvUserRepositoryImpl();
        BookRepository bookRepository = new CsvBookRepositoryImpl();
        OrderRepository orderRepository = new CsvOrderRepositoryImpl();
        InputService inputService = new InputServiceImpl();
        OutputService outputService = new OutputServiceImpl();
        UserService userService = new UserServiceImpl(userRepository);
        OrderService orderService = new OrderServiceImpl(orderRepository);
        BookService bookService = new BookServiceImpl(bookRepository);
        UIService uiService = new UIServiceImpl(orderService, userService, bookService, inputService, outputService);
        uiService.mainMenu();
    }
}
