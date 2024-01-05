package ru.fedbon.client.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.fedbon.client.BookServiceClient;
import ru.fedbon.client.dto.BookCreateDto;
import ru.fedbon.client.dto.BookDto;
import ru.fedbon.client.dto.BookUpdateDto;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookServiceClient bookServiceClient;

    @PostMapping(value = "/api/books")
    public BookDto handleCreate(@RequestBody BookCreateDto bookCreateDto) {
        return bookServiceClient.create(bookCreateDto);
    }

    @PutMapping(value = "/api/books/{id}")
    public BookDto handleUpdate(@PathVariable(value = "id") long id, @RequestBody BookUpdateDto bookUpdateDto) {
        return bookServiceClient.update(bookUpdateDto);
    }

    @GetMapping(value = "/api/books")
    public List<BookDto> handleGetAll() {
        return bookServiceClient.getAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @GetMapping(value = "/api/books", params = "authorId")
    public List<BookDto> handleGetAllByAuthorId(@RequestParam(value = "authorId") Long authorId) {
        return bookServiceClient.getAllByAuthorId(authorId);
    }

    @GetMapping(value = "/api/books", params = "genreId")
    public List<BookDto> handleGetAllByGenreId(@RequestParam(value = "genreId") Long genreId) {
        return bookServiceClient.getAllByGenreId(genreId);
    }

    @GetMapping(value = "/api/books/{id}")
    public BookDto handleGetById(@PathVariable(value = "id") long id) {
        return bookServiceClient.getById(id);
    }

    @DeleteMapping(value = "/api/books/{id}")
    public void handleDelete(@PathVariable(value = "id") long id) {
        bookServiceClient.deleteById(id);
    }

    @DeleteMapping(value = "/api/books")
    public void handleDeleteAll() {
        bookServiceClient.deleteAll();
    }
}

