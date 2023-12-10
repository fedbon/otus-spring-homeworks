package ru.fedbon.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.exception.NotFoundException;
import ru.fedbon.service.BookService;



@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/api/books")
    public ResponseEntity<Mono<BookDto>> handleCreate(@RequestBody BookCreateDto bookCreateDto) {
        Mono<BookDto> createdBook = bookService.create(bookCreateDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PutMapping(value = "/api/books/{id}")
    public ResponseEntity<Mono<BookDto>> handleUpdate(@PathVariable(value = "id") String id,
                                                      @RequestBody BookUpdateDto bookUpdateDto) {
        Mono<BookDto> updatedBook = bookService.update(bookUpdateDto);
        return ResponseEntity.ok(updatedBook);
    }

    @GetMapping(value = "/api/books")
    public ResponseEntity<Flux<BookDto>> handleGetAll() {
        Flux<BookDto> allBooks = bookService.getAll(Sort.by(Sort.Direction.ASC, "id"));
        return ResponseEntity.ok(allBooks);
    }

    @GetMapping(value = "/api/books", params = "authorId")
    public ResponseEntity<Flux<BookDto>> handleGetAllByAuthorId(@RequestParam(value = "authorId") String authorId) {
        Flux<BookDto> booksByAuthor = bookService.getAllByAuthorId(authorId);
        return ResponseEntity.ok(booksByAuthor);
    }

    @GetMapping(value = "/api/books", params = "genreId")
    public ResponseEntity<Flux<BookDto>> handleGetAllByGenreId(@RequestParam(value = "genreId") String genreId) {
        Flux<BookDto> booksByGenre = bookService.getAllByGenreId(genreId);
        return ResponseEntity.ok(booksByGenre);
    }

    @GetMapping(value = "/api/books/{id}")
    public ResponseEntity<Mono<BookDto>> handleGetById(@PathVariable(value = "id") String id) {
        Mono<BookDto> bookById = bookService.getById(id);
        return ResponseEntity.ok(bookById);
    }

    @DeleteMapping(value = "/api/books/{id}")
    public ResponseEntity<Mono<Void>> handleDelete(@PathVariable(value = "id") String id) {
        Mono<Void> deletedBook = bookService.deleteById(id);
        return ResponseEntity.ok(deletedBook);
    }

    @DeleteMapping(value = "/api/books")
    public ResponseEntity<Mono<Void>> handleDeleteAll() {
        Mono<Void> deletedAll = bookService.deleteAll();
        return ResponseEntity.ok(deletedAll);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    public ResponseEntity<String> handleNotFound(NotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }
}


