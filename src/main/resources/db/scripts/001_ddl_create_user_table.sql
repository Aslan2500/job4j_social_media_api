create table if not exists users
(
    id          UUID PRIMARY KEY,
    username    varchar(50) not null,
    email       varchar(100) not null,
    password    varchar(255) not null,
    created_at  timestamp default CURRENT_TIMESTAMP
);

comment on column users.username is 'Имя пользователя';
comment on column users.email is 'Электронная почта пользователя';
comment on column users.password is 'Пароль пользователя';
comment on column users.created_at is 'Дата и время регистрации пользователя';