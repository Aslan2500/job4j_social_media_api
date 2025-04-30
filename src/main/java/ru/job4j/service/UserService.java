package ru.job4j.service;

import ru.job4j.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    boolean updateUser(User user);

    Optional<User> getUserById(Long id);

    boolean deleteUserById(Long id);
}
