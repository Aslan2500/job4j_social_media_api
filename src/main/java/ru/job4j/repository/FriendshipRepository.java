package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.entity.Friendship;
import ru.job4j.entity.User;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    boolean existsBetween(User one, User two);

    Optional<Friendship> findByUsers(User one, User two);

    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.requester = ?1 AND f.addressee = ?2) OR (f.requester = ?2 AND f.addressee = ?1)")
    void deleteIfExistsBetween(User a, User b);
}
