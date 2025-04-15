package ru.jpb4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.entity.Post;
import ru.job4j.entity.User;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        postRepository.deleteAll();
    }

    @Test
    void whenSaveUserThenFindById() {
        var user = createUser();

        var post = new Post();
        post.setTitle("PostTitle");
        post.setContent("content");
        post.setImageUrl("image");
        post.setCreatedAt(OffsetDateTime.now());
        post.setUser(user);
        postRepository.save(post);
        var postFound = postRepository.findById(post.getId());
        assertThat(postFound).isPresent();
        assertThat(postFound.get().getTitle()).isEqualTo("PostTitle");
    }

    @Test
    void whenFindAllTheReturnAllPosts() {
        var user = createUser();
        createPost(user, "postTitleFirst", OffsetDateTime.now());
        createPost(user, "postTitleSecond", OffsetDateTime.now());

        List<Post> allPosts = postRepository.findAll();
        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getTitle).contains("postTitleFirst", "postTitleSecond");
    }

    @Test
    void whenFindAllByUserTheReturnAllPosts() {
        var user = createUser();
        createPost(user, "postTitleFirst", OffsetDateTime.now());
        createPost(user, "postTitleSecond", OffsetDateTime.now());

        List<Post> allPosts = postRepository.findAllByUser(user);
        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getTitle).contains("postTitleFirst", "postTitleSecond");
    }

    @Test
    void whenFindAllByCreatedBetweenTheReturnTwoPosts() {
        var user = createUser();
        createPost(user, "postTitleFirst", OffsetDateTime.now().plusDays(1));
        createPost(user, "postTitleSecond", OffsetDateTime.now().plusDays(10));

        List<Post> allPosts = postRepository.findAllByCreatedAtBetween(OffsetDateTime.now(), OffsetDateTime.now().plusDays(3));
        assertThat(allPosts).hasSize(1);
        assertThat(allPosts).extracting(Post::getTitle).contains("postTitleFirst");
    }

    @Test
    void whenFindAllOrderCreatedAtAscThenReturnAllPosts() {
        var user = createUser();
        createPost(user, "postTitleFirst", OffsetDateTime.now().plusDays(10));
        createPost(user, "postTitleSecond", OffsetDateTime.now().plusDays(2));
        createPost(user, "postTitleThird", OffsetDateTime.now().plusDays(4));
        Page<Post> allByOrderByCreatedAtDesc = postRepository.findAllByOrderByCreatedAtAsc(Pageable.ofSize(1));
        assertThat(allByOrderByCreatedAtDesc.getTotalPages()).isEqualTo(3);
        Optional<Post> first = allByOrderByCreatedAtDesc.get().findFirst();
        assertThat(first).isPresent();
        assertThat(first.get().getTitle()).isEqualTo("postTitleSecond");
    }

    private User createUser() {
        var user = new User();
        user.setUsername("UserNameFirst");
        user.setEmail("UserEmail");
        user.setPassword("UserPassword");
        user.setCreatedAt(OffsetDateTime.now());
        return userRepository.save(user);
    }

    private void createPost(User user, String title, OffsetDateTime createdAt) {
        var firstPost = new Post();
        firstPost.setTitle(title);
        firstPost.setContent("content");
        firstPost.setImageUrl("image");
        firstPost.setCreatedAt(OffsetDateTime.now().plusDays(1));
        firstPost.setCreatedAt(createdAt);
        firstPost.setUser(user);
        postRepository.save(firstPost);
    }
}
