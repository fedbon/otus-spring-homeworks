package ru.fedbon.repository;

import ru.fedbon.model.Genre;

import java.util.List;
import java.util.Optional;

public interface GenreRepository {
    long count();

    void save(Genre genre);

    Genre update(Genre genre);

    Optional<Genre> findById(long id);

    List<Genre> findAll();

    void deleteById(long id);

    long deleteAll();
}
