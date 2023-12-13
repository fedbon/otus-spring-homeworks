package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.service.AuthorService;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/api/authors")
    @ResponseStatus(HttpStatus.OK)
    public Flux<AuthorDto> handleGetAll() {
        return authorService.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }
}
