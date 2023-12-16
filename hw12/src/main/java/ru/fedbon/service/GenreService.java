package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.model.Genre;

import java.util.List;

public interface GenreService {

    List<Genre> getAll(Sort sort);
}
