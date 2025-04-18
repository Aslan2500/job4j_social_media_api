package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.entity.Subscription;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
}
