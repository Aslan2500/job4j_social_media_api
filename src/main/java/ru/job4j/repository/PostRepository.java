package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.entity.Post;

import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {
}
