package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.entity.User;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
}
