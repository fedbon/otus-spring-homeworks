merge into authors as target
    using (values ('Лев Толстой'),
                  ('Николай Гоголь'),
                  ('Фёдор Достоевский'),
                  ('Станислав Лем'),
                  ('Михаил Булгаков')) as source (name)
    on target.name = source.name
    when not matched then
        insert (name) values (source.name);

merge into genres as target
    using (values ('Русская классика'),
                  ('Фантастическая литература'),
                  ('Фэнтези'),
                  ('Ужасы'),
                  ('Сатира')) as source (genre)
    on target.genre = source.genre
    when not matched then
        insert (genre) values (source.genre);

merge into books as target
    using (values (1, 1, 'Война и мир'),
                  (4, 2, 'Мёртвые души'),
                  (1, 3, 'Преступление и наказание'),
                  (2, 4, 'Солярис'),
                  (3, 3, 'Идиот'),
                  (4, 5, 'Мастер и Маргарита')) as source (genre_id, author_id, title)
    on target.genre_id = source.genre_id and target.author_id = source.author_id and target.title = source.title
    when not matched then
        insert (genre_id, author_id, title) values (source.genre_id, source.author_id, source.title);
