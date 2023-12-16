package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.mapper.AuthorMapper;
import ru.fedbon.repository.AuthorRepository;


@RestController
@RequiredArgsConstructor
public class AuthorController {

    private final AuthorRepository authorRepository;

    @GetMapping(value = "/api/authors")
    public Flux<AuthorDto> handleGetAll() {
        return authorRepository.findAll(Sort.by(Sort.Direction.ASC, "id"))
                .map(AuthorMapper::mapAuthorToDto);
    }
}
