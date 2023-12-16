create table if not exists authors
(
    id   bigserial primary key,
    name varchar(255)
    );

create table if not exists genres
(
    id    bigserial primary key,
    name varchar(255)
    );

create table if not exists books
(
    id        bigserial primary key,
    genre_id  bigint references genres (id),
    author_id bigint references authors (id),
    title     varchar(255)
);