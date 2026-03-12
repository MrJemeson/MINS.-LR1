package service;

import object.User;

import java.util.Optional;

public interface UserService {
    User getUserById(int userId);
    User getUserByName(String userName);
}
