insert into authors (name)
values ('Лев Толстой'),
       ('Николай Гоголь'),
       ('Фёдор Достоевский'),
       ('Станислав Лем'),
       ('Михаил Булгаков');

insert into genres (genre)
values ('Русская классика'),
       ('Фантастическая литература'),
       ('Фэнтези'),
       ('Ужасы'),
       ('Сатира');

insert into books (genre_id, author_id, title)
values (1, 1, 'Война и мир'),
       (4, 2, 'Мёртвые души'),
       (1, 3, 'Преступление и наказание'),
       (2, 4, 'Солярис'),
       (3, 3, 'Идиот'),
       (4, 5, 'Мастер и Маргарита');

insert into comments (book_id, comment)
values (1, 'Комментарий 1 к книге 1'),
       (2, 'Комментарий 1 к книге 2'),
       (2, 'Комментарий 2 к книге 2'),
       (3, 'Комментарий 1 к книге 3'),
       (4, 'Комментарий 1 к книге 4'),
       (1, 'Комментарий 2 к книге 1'),
       (4, 'Комментарий 2 к книге 4'),
       (5, 'Комментарий 1 к книге 5'),
       (6, 'Комментарий 1 к книге 6'),
       (3, 'Комментарий 2 к книге 3'),
       (6, 'Комментарий 2 к книге 6'),
       (5, 'Комментарий 2 к книге 5');

insert into users (username, password, authority)
values ('adm', '$2a$12$8pnMmhZwwsHvAQNcti0rGe/w3L24vwBNbondgNEzYCMcoNi7XsRqS', 'ROLE_ADMIN'),
       ('usr', '$2a$12$RikQvoFNSmWkX1SbfWQ9QOu2gZElz.i8w84uNMcbfUpAAwzcXMHwe', 'ROLE_USER');
