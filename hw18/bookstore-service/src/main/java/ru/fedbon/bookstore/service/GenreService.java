package ru.fedbon.bookstore.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.bookstore.dto.GenreDto;


import java.util.List;

public interface GenreService {

    List<GenreDto> getAll(Sort sort);

}
