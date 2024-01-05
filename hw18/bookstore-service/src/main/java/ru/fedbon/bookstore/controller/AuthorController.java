package ru.fedbon.bookstore.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fedbon.bookstore.dto.AuthorDto;
import ru.fedbon.bookstore.service.AuthorService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping(value = "/api/authors")
    public List<AuthorDto> handleGetAll() {
        return authorService.getAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}
