package ru.job4j.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.entity.Friendship;
import ru.job4j.entity.User;
import ru.job4j.entity.enums.Status;
import ru.job4j.repository.FriendshipRepository;
import ru.job4j.service.FriendshipService;
import ru.job4j.service.SubscriptionService;

import java.time.OffsetDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;
    private final SubscriptionService subscriptionService;

    /**
     * Отправить заявку на дружбу и подписаться на пользователя
     */
    @Transactional
    public void sendFriendRequest(User currentUser, User sendRequestTo) {
        subscriptionService.subscribe(currentUser, sendRequestTo);

        if (!friendshipRepository.existsBetween(currentUser, sendRequestTo)) {
            var friendship = new Friendship();
            friendship.setRequester(currentUser);
            friendship.setAddressee(sendRequestTo);
            friendship.setStatus(Status.PENDING);
            friendship.setCreatedAt(OffsetDateTime.now());
            friendshipRepository.save(friendship);
        }
    }

    /**
     * Принять дружбу и подписаться в ответ
     */
    @Transactional
    public void acceptFriendRequest(User currentUser, User toAccept) {
        Friendship friendship = friendshipRepository.findByUsers(currentUser, toAccept)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        friendship.setStatus(Status.ACCEPTED);
        friendshipRepository.save(friendship);

        // Создаём обратную подписку
        subscriptionService.subscribe(currentUser, toAccept);
    }

    /**
     * Отклонение заявки (подписка сохраняется)
     */
    @Transactional
    public void declineFriendRequest(User currentUser, User to) {
        Friendship friendship = friendshipRepository.findByUsers(currentUser, to)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        friendship.setStatus(Status.DECLINED);
        friendshipRepository.save(friendship);
    }

    /**
     * Удаляем друга и отписываемся от него
     * При этом он все еще остается нашим подписчиком
     */
    @Transactional
    public void removeFriend(User currentUser, User toDelete) {
        friendshipRepository.deleteIfExistsBetween(currentUser, toDelete);
        subscriptionService.unsubscribe(currentUser, toDelete);
    }
}
