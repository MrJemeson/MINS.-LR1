package repository;

import object.User;

import java.util.Optional;

public interface UserRepository {
    Optional<User> findByName(String userName);
    Optional<User> findByUserId(int userId);
}
