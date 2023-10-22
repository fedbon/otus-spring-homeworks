create table if not exists authors
(
    id   bigserial primary key,
    name varchar(255) not null unique
);

create table if not exists genres
(
    id    bigserial primary key,
    genre varchar(255) not null unique
);

create table if not exists books
(
    id        bigserial primary key,
    genre_id  bigint references genres (id) on delete cascade on update cascade,
    author_id bigint references authors (id) on delete cascade on update cascade,
    title     varchar(255) not null
);

create table if not exists book_comments
(
    id      bigserial primary key,
    book_id bigint references books (id) on delete cascade on update cascade,
    comment varchar(1000) not null
);