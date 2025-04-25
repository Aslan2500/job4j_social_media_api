package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.entity.Subscription;
import ru.job4j.entity.User;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    boolean existsBySubscriberAndTarget(User subscriber, User target);

    void deleteBySubscriberAndTarget(User subscriber, User target);
}
