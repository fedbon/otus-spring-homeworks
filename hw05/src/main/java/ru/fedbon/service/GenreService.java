package ru.fedbon.service;

import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getGenresCount();

    Genre addGenre(String genreName);

    Genre changeGenre(long id, String genreName);

    Genre getGenreById(long id);

    Genre getGenreByName(String genreName);

    List<Genre> getAllGenres();

    Genre deleteGenreById(long id);

    long deleteAllGenres();
}
