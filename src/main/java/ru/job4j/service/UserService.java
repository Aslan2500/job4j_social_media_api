package ru.job4j.service;

import ru.job4j.entity.User;

import java.util.Optional;

public interface UserService {

    User saveUser(User user);

    Optional<User> getUserById(Long id);

    void deleteUserById(Long id);
}
