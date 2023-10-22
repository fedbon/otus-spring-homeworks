package ru.fedbon.service;

import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getCount();

    Genre add(String genreName);

    void change(Genre genre);

    List<Genre> getAll();

    void deleteById(long id);

    long deleteAll();
}
