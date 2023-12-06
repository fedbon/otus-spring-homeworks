package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import ru.fedbon.dto.GenreDto;


import java.util.List;

public interface GenreService {

    List<GenreDto> getAll(Sort sort);

}
