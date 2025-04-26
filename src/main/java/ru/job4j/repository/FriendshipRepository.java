package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.entity.Friendship;
import ru.job4j.entity.User;

import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    @Query("""
    SELECT CASE WHEN COUNT(f) > 0 THEN true ELSE false END
    FROM Friendship f
    WHERE (f.requester = :user1 AND f.addressee = :user2)
       OR (f.requester = :user2 AND f.addressee = :user1)
    """)
    boolean existsBetween(User one, User two);

    @Query("""
    SELECT f FROM Friendship f
    WHERE (f.requester = :user1 AND f.addressee = :user2)
       OR (f.requester = :user2 AND f.addressee = :user1)
    """)
    Optional<Friendship> findByUsers(User one, User two);

    @Modifying
    @Query("DELETE FROM Friendship f WHERE (f.requester = ?1 AND f.addressee = ?2) OR (f.requester = ?2 AND f.addressee = ?1)")
    void deleteIfExistsBetween(User a, User b);
}
