package service;

import object.Book;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> getOpenBooksByNameOrAuthor(String searchString);
    Book getById(int bookId);
    void takeBook(int bookId);
    void returnBook(int bookId);
}
