create table if not exists subscription
(
    id              UUID primary key,
    follower     UUID not null,
    following    UUID not null,

    foreign key (follower) references users(id) on delete cascade,
    foreign key (following) references users(id) on delete cascade
);

comment on column subscription.follower is 'ID пользователя, который подписался';
comment on column subscription.following is 'ID пользователя, на которого подписались';