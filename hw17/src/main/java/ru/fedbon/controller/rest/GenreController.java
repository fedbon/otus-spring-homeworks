package ru.fedbon.controller.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fedbon.dto.GenreDto;
import ru.fedbon.service.GenreService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class GenreController {

    private final GenreService genreService;

    @GetMapping(value = "/api/genres")
    public List<GenreDto> handleGetAllGenres() {
        return genreService.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
