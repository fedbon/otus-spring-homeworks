package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {

    Genre create(String name);

    Genre update(Genre genreDto);

    List<Genre> getAll(Sort sort);

    Genre getById(long id);

    void deleteById(long id);

    void deleteAll();
}
