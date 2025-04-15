package ru.jpb4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.entity.Subscription;
import ru.job4j.entity.User;
import ru.job4j.repository.SubscriptionRepository;
import ru.job4j.repository.UserRepository;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        subscriptionRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSaveSubscriptionThenFindById() {
        var follower = new User();
        follower.setUsername("UserName");
        follower.setEmail("UserEmail");
        follower.setPassword("UserPassword");
        follower.setCreatedAt(OffsetDateTime.now());
        userRepository.save(follower);

        var following = new User();
        following.setUsername("UserName");
        following.setEmail("UserEmail");
        following.setPassword("UserPassword");
        following.setCreatedAt(OffsetDateTime.now());
        userRepository.save(following);

        var sub = new Subscription();
        sub.setFollowerId(follower);
        sub.setFollowingId(following);
        subscriptionRepository.save(sub);

        var subFound = subscriptionRepository.findById(sub.getId());
        assertThat(subFound).isPresent();
        assertThat(subFound.get().getFollowerId().getId()).isEqualTo(follower.getId());
    }

    @Test
    void whenFindAllThenReturnAllSubscriptions() {
        var follower = new User();
        follower.setUsername("UserName");
        follower.setEmail("UserEmail");
        follower.setPassword("UserPassword");
        follower.setCreatedAt(OffsetDateTime.now());
        userRepository.save(follower);

        var following = new User();
        following.setUsername("UserName");
        following.setEmail("UserEmail");
        following.setPassword("UserPassword");
        following.setCreatedAt(OffsetDateTime.now());
        userRepository.save(following);

        var subFirst = new Subscription();
        subFirst.setFollowerId(follower);
        subFirst.setFollowingId(following);
        subscriptionRepository.save(subFirst);

        var subSecond = new Subscription();
        subSecond.setFollowerId(follower);
        subSecond.setFollowingId(following);
        subscriptionRepository.save(subSecond);

        var allSubs = subscriptionRepository.findAll();
        assertThat(allSubs).hasSize(2);
    }
}
