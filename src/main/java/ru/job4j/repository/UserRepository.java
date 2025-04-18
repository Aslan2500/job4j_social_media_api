package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT user FROM User user
            WHERE user.email = ?1 and user.password = ?2""")
    Optional<User> findByEmailAndPassword(String email, String password);

    @Query(value = """
            SELECT s.follower FROM Subscription s WHERE s.following = ?1
            """)
    List<User> findAllFollowersOfUser(User user);

    @Query(value = """
            SELECT CASE
                     WHEN f.requester = ?1 THEN f.addressee
                     ELSE f.requester
                   END
            FROM Friendship f
            WHERE (f.requester = ?1 OR f.addressee = ?1)
            AND f.status = 'ACCEPTED'
            """)
    List<User> findAllFriendsForUser(User user);
}
