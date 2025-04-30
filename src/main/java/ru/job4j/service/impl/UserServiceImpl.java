package ru.job4j.service.impl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.entity.User;
import ru.job4j.repository.UserRepository;
import ru.job4j.service.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        var optionalUser = userRepository.findById(user.getId());
        if (optionalUser.isEmpty()) {
            return false;
        }
        User userToUpdate = optionalUser.get();
        userToUpdate.setUsername(user.getUsername());
        userToUpdate.setEmail(user.getEmail());
        userRepository.save(userToUpdate);
        return true;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public boolean deleteUserById(Long id) {
        var optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return false;
        }
        userRepository.deleteById(id);
        return true;
    }
}
