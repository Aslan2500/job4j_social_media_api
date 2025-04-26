package ru.jpb4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.entity.Friendship;
import ru.job4j.entity.User;
import ru.job4j.entity.enums.Status;
import ru.job4j.repository.FriendshipRepository;
import ru.job4j.repository.UserRepository;

import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class  FriendshipRepositoryTest {

    @Autowired
    private FriendshipRepository friendshipRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        friendshipRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void whenSaveFriendshipThenFindById() {
        var requester = new User();
        requester.setUsername("UserName");
        requester.setEmail("UserEmail");
        requester.setPassword("UserPassword");
        requester.setCreatedAt(OffsetDateTime.now());
        userRepository.save(requester);

        var addressee = new User();
        addressee.setUsername("UserName");
        addressee.setEmail("UserEmail");
        addressee.setPassword("UserPassword");
        addressee.setCreatedAt(OffsetDateTime.now());
        userRepository.save(addressee);

        var friendship = new Friendship();
        friendship.setRequester(requester);
        friendship.setAddressee(addressee);
        friendship.setCreatedAt(OffsetDateTime.now());
        friendship.setStatus(Status.ACCEPTED);
        friendshipRepository.save(friendship);
        var friendshipFound = friendshipRepository.findById(friendship.getId());
        assertThat(friendshipFound).isPresent();
        assertThat(friendshipFound.get().getStatus()).isEqualTo(Status.ACCEPTED);
    }

    @Test
    void whenFindAllThenReturnAllFriendship() {
        var requester = new User();
        requester.setUsername("UserName");
        requester.setEmail("UserEmail");
        requester.setPassword("UserPassword");
        requester.setCreatedAt(OffsetDateTime.now());
        userRepository.save(requester);

        var addressee = new User();
        addressee.setUsername("UserName");
        addressee.setEmail("UserEmail");
        addressee.setPassword("UserPassword");
        addressee.setCreatedAt(OffsetDateTime.now());
        userRepository.save(addressee);

        var friendshipFirst = new Friendship();
        friendshipFirst.setRequester(requester);
        friendshipFirst.setAddressee(addressee);
        friendshipFirst.setCreatedAt(OffsetDateTime.now());
        friendshipFirst.setStatus(Status.ACCEPTED);
        friendshipRepository.save(friendshipFirst);

        var friendshipSecond = new Friendship();
        friendshipSecond.setRequester(requester);
        friendshipSecond.setAddressee(addressee);
        friendshipSecond.setCreatedAt(OffsetDateTime.now());
        friendshipSecond.setStatus(Status.DECLINED);
        friendshipRepository.save(friendshipSecond);

        var allFriendShip = friendshipRepository.findAll();
        assertThat(allFriendShip).hasSize(2);
        assertThat(allFriendShip).extracting(Friendship::getStatus).contains(Status.ACCEPTED, Status.DECLINED);
    }
}
