package ru.job4j.service;

import ru.job4j.entity.User;

public interface SubscriptionService {

    void subscribe(User subscriber, User target);

    void unsubscribe(User subscriber, User targetToUnsubscribe);
}
