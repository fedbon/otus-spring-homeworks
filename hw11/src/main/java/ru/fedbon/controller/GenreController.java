package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.GenreDto;
import ru.fedbon.service.GenreService;



@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/api/genres")
    @ResponseStatus(HttpStatus.OK)
    public Flux<GenreDto> handleGetAll() {
        return genreService.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
