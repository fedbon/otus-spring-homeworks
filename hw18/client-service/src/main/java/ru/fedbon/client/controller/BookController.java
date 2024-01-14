package ru.fedbon.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fedbon.client.feign.BookServiceClient;
import ru.fedbon.client.dto.BookDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookServiceClient bookServiceClient;

    @GetMapping(value = "/api/books")
    public List<BookDto> handleGetAll() {
        return bookServiceClient.getAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}

