package ru.fedbon.service;

import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getCount();

    Genre add(String name);

    Genre change(Genre genreDto);

    List<Genre> getAll();

    Genre getById(long id);

    void deleteById(long id);

    long deleteAll();
}
