create table if not exists friendship
(
    id              UUID primary key,
    requester_id    UUID not null ,
    addressee_id    UUID not null ,
    status          varchar(20) not null ,
    created_at      timestamp default current_timestamp,

    foreign key (requester_id) references users(id) on delete cascade,
    foreign key (addressee_id) references users(id) on delete cascade
);

comment on column friendship.requester_id is 'ID пользователя, отправившего заявку на дружбу';
comment on column friendship.addressee_id is 'ID пользователя, которому отправлена заявка';
comment on column friendship.status is 'Статус заявки';
comment on column friendship.created_at is 'Дата и время заявки';