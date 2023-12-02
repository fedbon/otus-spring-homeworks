package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {
    long getCount();

    Genre create(String name);

    Genre update(Genre genreDto);

    List<Genre> getAll(Sort sort);

    Genre getById(String id);

    void deleteById(String id);

    void deleteAll();
}
