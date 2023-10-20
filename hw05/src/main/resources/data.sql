insert into authors(name)
values ('Лев Толстой'),
       ('Николай Гоголь'),
       ('Фёдор Достоевский'),
       ('Станислав Лем'),
       ('Михаил Булгаков');

insert into genres(genre)
values ('Русская классика'),
       ('Фантастическая литература'),
       ('Фэнтези'),
       ('Ужасы'),
       ('Сатира');

insert into books(genre_id, author_id, title)
values (1, 1, 'Война и мир'),
       (4, 2, 'Мёртвые души'),
       (1, 3, 'Преступление и наказание'),
       (2, 4, 'Солярис'),
       (3, 3, 'Идиот'),
       (4, 5, 'Мастер и Маргарита');