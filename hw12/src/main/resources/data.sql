merge into authors as target
    using (values ('Лев Толстой'),
                  ('Николай Гоголь'),
                  ('Фёдор Достоевский'),
                  ('Станислав Лем'),
                  ('Михаил Булгаков'))
        as source (name)
    on target.name = source.name
    when not matched then
        insert (name) values (source.name);

merge into genres as target
    using (values ('Русская классика'),
                  ('Фантастическая литература'),
                  ('Фэнтези'),
                  ('Ужасы'),
                  ('Сатира'))
        as source (genre)
    on target.genre = source.genre
    when not matched then
        insert (genre) values (source.genre);

merge into books as target
    using (values (1, 1, 'Война и мир'),
                  (4, 2, 'Мёртвые души'),
                  (1, 3, 'Преступление и наказание'),
                  (2, 4, 'Солярис'),
                  (3, 3, 'Идиот'),
                  (4, 5, 'Мастер и Маргарита'))
        as source (genre_id, author_id, title)
    on target.genre_id = source.genre_id
           and target.author_id = source.author_id
           and target.title = source.title
    when not matched then
        insert (genre_id, author_id, title) values (source.genre_id, source.author_id, source.title);

merge into comments as target
    using (values (1, 'Комментарий 1 к книге 1'),
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
                  (5, 'Комментарий 2 к книге 5'))
        as source (book_id, comment)
    on target.book_id = source.book_id
           and target.comment = source.comment
    when not matched then
        insert (book_id, comment) values (source.book_id, source.comment);

merge into users as target
    using (values
               ('usr', '$2a$12$Bpjf1r/yJzwRF5YgYyEghOK/fx57KL.073M6YyYJgfzIHR6dqAeo6'))
        as source (username, password)
    on target.username = source.username
    when not matched then
        insert (username, password) values (source.username, source.password);