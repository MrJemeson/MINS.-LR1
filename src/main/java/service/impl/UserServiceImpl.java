package service.impl;

import exception.NoSuchUserException;
import object.User;
import repository.UserRepository;
import service.UserService;

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(int userId) {
        return userRepository.findByUserId(userId).orElseThrow(() -> new NoSuchUserException("No such user with id " + userId));
    }

    @Override
    public User getUserByName(String userName) {
        return userRepository.findByName(userName).orElseThrow(() -> new NoSuchUserException("No such user with username " + userName));
    }
}
