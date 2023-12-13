package ru.fedbon.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.service.BookService;



@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/api/books")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<BookDto> handleCreate(@Valid @RequestBody BookCreateDto bookCreateDto) {
        return bookService.create(bookCreateDto);
    }

    @PutMapping(value = "/api/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> handleUpdate(@PathVariable(value = "id") String id,
                                      @Valid @RequestBody BookUpdateDto bookUpdateDto) {
        return bookService.update(bookUpdateDto);
    }

    @GetMapping(value = "/api/books")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> handleGetAll() {
        return bookService.getAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @GetMapping(value = "/api/books", params = "authorId")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> handleGetAllByAuthorId(@RequestParam(value = "authorId") String authorId) {
        return bookService.getAllByAuthorId(authorId);
    }

    @GetMapping(value = "/api/books", params = "genreId")
    @ResponseStatus(HttpStatus.OK)
    public Flux<BookDto> handleGetAllByGenreId(@RequestParam(value = "genreId") String genreId) {
        return bookService.getAllByGenreId(genreId);
    }

    @GetMapping(value = "/api/books/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Mono<BookDto> handleGetById(@PathVariable(value = "id") String id) {
        return bookService.getById(id);
    }

    @DeleteMapping(value = "/api/books/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> handleDelete(@PathVariable(value = "id") String id) {
        return bookService.deleteById(id);
    }

    @DeleteMapping(value = "/api/books")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> handleDeleteAll() {
        return bookService.deleteAll();
    }
}


