package ru.fedbon.service;

import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getCount();

    void add(String genreName);

    Genre change(Genre genre);

    List<Genre> getAll();

    Genre getById(long id);

    void deleteById(long id);

    long deleteAll();
}
