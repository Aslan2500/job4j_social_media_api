package ru.jpb4j;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.job4j.Job4jSocialMediaApiApplication;
import ru.job4j.entity.User;
import ru.job4j.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = Job4jSocialMediaApiApplication.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    @Test
    void whenSaveUserThenFindById() {
        var user = new User();
        user.setUsername("UserName");
        user.setEmail("UserEmail");
        user.setPassword("UserPassword");
        user.setCreatedAt(OffsetDateTime.now());
        userRepository.save(user);
        var userFound = userRepository.findById(user.getId());
        assertThat(userFound).isPresent();
        assertThat(userFound.get().getUsername()).isEqualTo("UserName");
    }

    @Test
    void whenFindAllThenReturnAllUsers() {
        var userFirst = new User();
        userFirst.setUsername("UserNameFirst");
        userFirst.setEmail("UserEmail");
        userFirst.setPassword("UserPassword");
        userFirst.setCreatedAt(OffsetDateTime.now());
        userRepository.save(userFirst);

        var userSecond = new User();
        userSecond.setUsername("UserNameSecond");
        userSecond.setEmail("UserEmail");
        userSecond.setPassword("UserPassword");
        userSecond.setCreatedAt(OffsetDateTime.now());
        userRepository.save(userSecond);

        List<User> allUsers = userRepository.findAll();
        assertThat(allUsers).hasSize(2);
        assertThat(allUsers).extracting(User::getUsername).contains("UserNameFirst", "UserNameSecond");
    }
}
