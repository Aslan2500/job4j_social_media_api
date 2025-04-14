package ru.jpb4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.entity.Post;
import ru.job4j.entity.User;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PostRepositoryTest {

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
        var user = new User();
        user.setUsername("UserNameFirst");
        user.setEmail("UserEmail");
        user.setPassword("UserPassword");
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);

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
        var user = new User();
        user.setUsername("UserNameFirst");
        user.setEmail("UserEmail");
        user.setPassword("UserPassword");
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);

        var firstPost = new Post();
        firstPost.setTitle("postTitleFirst");
        firstPost.setContent("content");
        firstPost.setImageUrl("image");
        firstPost.setCreatedAt(OffsetDateTime.now());
        firstPost.setUser(user);
        postRepository.save(firstPost);

        var secondPost = new Post();
        secondPost.setTitle("postTitleFirst");
        secondPost.setContent("content");
        secondPost.setImageUrl("image");
        secondPost.setCreatedAt(OffsetDateTime.now());
        secondPost.setUser(user);
        postRepository.save(secondPost);

        List<Post> allPosts = postRepository.findAll();
        assertThat(allPosts).hasSize(2);
        assertThat(allPosts).extracting(Post::getTitle).contains("postTitleFirst", "postTitleFirst");
    }
}
