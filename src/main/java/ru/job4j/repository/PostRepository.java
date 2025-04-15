package ru.job4j.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.entity.Post;
import ru.job4j.entity.User;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

public interface PostRepository extends JpaRepository<Post, UUID> {

    List<Post> findAllByUser(User user);

    List<Post> findAllByCreatedAtBetween(OffsetDateTime createdAt, OffsetDateTime createdBefore);

    Page<Post> findAllByOrderByCreatedAtAsc(Pageable pageable);
}
