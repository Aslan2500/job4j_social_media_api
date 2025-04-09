create table if not exists post
(
    id          UUID primary key,
    user_id     UUID primary key not null,
    title       varchar(255) not null,
    content     text not null ,
    image_url   varchar(255),
    created_at  timestamp default current_timestamp,
    foreign key (user_id) references users(id) on delete cascade
);

comment on column post.user_id is 'ID Пользователя, создавшего пост';
comment on column post.title is 'Заголовок поста';
comment on column post.content is 'Текст поста';
comment on column post.image_url is 'Ссылка на изображение';
comment on column post.created_at is 'Дата и время создания поста';