package ru.fedbon.service;

import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getGenresCount();

    void addGenre(String genreName);

    Genre changeGenre(long id, String genreName);

    List<Genre> getAllGenres();

    Genre getGenreById(long id);

    void deleteGenreById(long id);

    long deleteAllGenres();
}
