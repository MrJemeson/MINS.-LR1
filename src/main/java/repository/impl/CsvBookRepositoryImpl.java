package repository.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import object.Book;
import object.User;
import repository.BookRepository;

import java.io.*;
import java.util.*;

public class CsvBookRepositoryImpl implements BookRepository {
    private final String csvHeader = "\"ID\",\"BookName\",\"AuthorName\",\"Taken\"";

    private List<Book> loadBooks(){
        List<Book> books = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("data/books.csv"))) {
            String[] parts;
            boolean firstLine = true;

            while ((parts = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                try {
                    books.add(parseCsvLine(parts));
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", parts));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return books;
    }

    private void saveBooks(List<Book> books) {
        try {
            File file = new File("data/books.csv");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                writer.println(csvHeader);
                for (Book book : books) {
                    writer.println(toCsvLine(book));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }

    public Book parseCsvLine(String[] parts) {
        if (parts.length !=4) {
            throw new IllegalArgumentException("Invalid CSV format");
        }
        int id = Integer.parseInt(parts[0].trim());
        String bookName = parts[1].trim();
        String authorName = parts[2].trim();
        boolean takenStatus = (parts[3].trim().equals("true"));
        return new Book(id, bookName, authorName, takenStatus);
    }

    public String toCsvLine(Book book) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(Integer.toString(book.getId()))
                .add(book.getBookName())
                .add(book.getAuthorName())
                .add((book.isTakenStatus())?("true"):("false"));
        return joiner.toString();
    }
    @Override
    public Optional<Book> findById(int bookId) {
        List<Book> books = loadBooks();
        Book book;
        try {
            book = books.stream().filter(x -> x.getId() == bookId).toList().getFirst();
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.of(book);
    }

    @Override
    public Optional<List<Book>> findByName(String bookName) {
        List<Book> books = loadBooks();
        books = books.stream().filter(x -> x.getBookName().toLowerCase().equals(bookName)).toList();
        if (books.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(books);
    }

    @Override
    public Optional<List<Book>> findByAuthor(String authorName) {
        List<Book> books = loadBooks();
        books = books.stream().filter(x -> x.getAuthorName().toLowerCase().equals(authorName)).toList();
        if (books.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(books);
    }

    @Override
    public void changeStatus(int bookId, Boolean isTaken) {
        List<Book> books = loadBooks();
        Book book = books.stream().filter(x -> x.getId() == bookId).toList().getFirst();
        book.setTakenStatus(isTaken);
        saveBooks(books);
    }
}