package repository.impl;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import object.User;
import repository.UserRepository;

import java.io.*;
import java.util.*;

public class CsvUserRepositoryImpl implements UserRepository {
    private final String csvHeader = "\"ID\",\"UserName\"";

    private List<User> loadUsers(){
        List<User> users = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new FileReader("data/users.csv"))) {
            String[] parts;
            boolean firstLine = true;

            while ((parts = reader.readNext()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                try {
                    users.add(parseCsvLine(parts));
                } catch (Exception e) {
                    System.err.println("Error parsing line: " + String.join(",", parts));
                    e.printStackTrace();
                }
            }
        } catch (IOException | CsvValidationException e) {
            e.printStackTrace();
        }

        return users;
    }

    private void saveUsers(List<User> users) {
        try {
            File file = new File("data/users.csv");
            try (PrintWriter writer = new PrintWriter(new FileWriter(file, false))) {
                writer.println(csvHeader);
                for (User user : users) {
                    writer.println(toCsvLine(user));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write CSV", e);
        }
    }

    public User parseCsvLine(String[] parts) {
        if (parts.length < 2) {
            throw new IllegalArgumentException("Invalid CSV format");
        }

        int id = Integer.parseInt(parts[0].trim());
        String userName = parts[1].trim();

        return new User(id, userName);
    }

    public String toCsvLine(User user) {
        StringJoiner joiner = new StringJoiner(",");
        joiner.add(Integer.toString(user.getId()))
                .add(user.getUserName());
        return joiner.toString();
    }

    @Override
    public Optional<User> findByName(String userName) {
        List<User> users = loadUsers();
        User user;
        try {
            user = users.stream().filter(x -> x.getUserName().equals(userName)).toList().getFirst();
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }

    @Override
    public Optional<User> findByUserId(int userId) {
        List<User> users = loadUsers();
        User user;
        try {
            user = users.stream().filter(x -> x.getId() == userId).toList().getFirst();
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
        return Optional.of(user);
    }
}
