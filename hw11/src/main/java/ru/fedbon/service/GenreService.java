package ru.fedbon.service;

import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.GenreDto;



public interface GenreService {

    Flux<GenreDto> getAll(Sort sort);

}
