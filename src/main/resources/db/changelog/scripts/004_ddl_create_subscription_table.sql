create table if not exists subscription
(
    id              UUID primary key,
    follower_id     UUID not null,
    following_id    UUID not null,

    foreign key (follower_id) references users(id) on delete cascade,
    foreign key (following_id) references users(id) on delete cascade
);

comment on column subscription.follower_id is 'ID пользователя, который подписался';
comment on column subscription.following_id is 'ID пользователя, на которого подписались';