package ru.fedbon.repository;

import ru.fedbon.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();

    Genre insert(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> findById(long id);

    Optional<Genre> findByName(String genreName);

    List<Genre> findAll();

    void deleteById(long id);

    long deleteAll();
}
