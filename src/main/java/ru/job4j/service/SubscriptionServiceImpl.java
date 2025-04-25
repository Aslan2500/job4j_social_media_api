package ru.job4j.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.job4j.entity.Subscription;
import ru.job4j.entity.User;
import ru.job4j.repository.SubscriptionRepository;

@Slf4j
@Service
@RequiredArgsConstructor
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;

    @Override
    public void subscribe(User subscriber, User target) {
        if (!subscriptionRepository.existsBySubscriberAndTarget(subscriber, target)) {
            var subscription = new Subscription();
            subscription.setSubscriber(subscriber);
            subscription.setTarget(target);
            subscriptionRepository.save(subscription);
        }
    }

    @Override
    public void unsubscribe(User subscriber, User targetToUnsubscribe) {
        subscriptionRepository.deleteBySubscriberAndTarget(subscriber, targetToUnsubscribe);
    }
}
