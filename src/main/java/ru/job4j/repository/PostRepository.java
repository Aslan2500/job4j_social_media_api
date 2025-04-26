package ru.job4j.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.entity.Post;
import ru.job4j.entity.User;

import java.time.OffsetDateTime;
import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllByUser(User user);

    List<Post> findAllByCreatedAtBetween(OffsetDateTime createdAt, OffsetDateTime createdBefore);

    Page<Post> findAllByOrderByCreatedAtAsc(Pageable pageable);

    @Modifying
    @Query(value = """
            UPDATE Post post
            SET post.title = ?1, post.content = ?2
            WHERE post = ?3
            """)
    int updateTitleAndContent(String newTitle, String newContent, Post post);

    @Modifying
    @Query(value = """
            UPDATE Post post
            SET post.imageUrl = null
            where post = ?1
            """)
    int deleteImageUrl(Post post);

    @Modifying
    @Query(value = """
            DELETE FROM Post as post where post = ?1
            """)
    int deletePost(Post post);

    @Query(value = """
           SELECT p FROM Post as p
           WHERE p.user IN (
                    SELECT s.target FROM Subscription s WHERE s.subscriber = ?1
                    )
           ORDER BY p.createdAt DESC
           """)
    Page<Post> findPostsOfSubscriptions(User user, Pageable pageable);
}
