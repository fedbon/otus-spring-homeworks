package ru.fedbon.controller;

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
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.dto.UpdateBookDto;
import ru.fedbon.service.BookService;

import java.util.List;


@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping(value = "/api/books")
    public BookDto handleCreate(@RequestBody NewBookDto newBookDto) {
        var createdBook = bookService.create(newBookDto);
        return BookDto.transformDomainToDto(createdBook);
    }

    @PutMapping(value = "/api/books/{id}")
    public BookDto handleUpdate(@PathVariable(value = "id") long id, @RequestBody UpdateBookDto updateBookDto) {
        var updatedBook = bookService.update(updateBookDto);
        return BookDto.transformDomainToDto(updatedBook);
    }

    @GetMapping(value = "/api/books")
    public List<BookDto> handleGetAll() {
        return bookService.getAll(Sort.by(Sort.Direction.ASC,"id"))
                .stream()
                .map(BookDto::transformDomainToDto)
                .toList();
    }

    @GetMapping(value = "/api/books", params = "authorId")
    public List<BookDto> handleGetAllByAuthorId(@RequestParam(value = "authorId") Long authorId) {
        return bookService.getAllByAuthorId(authorId)
                .stream()
                .map(BookDto::transformDomainToDto)
                .toList();
    }

    @GetMapping(value = "/api/books", params = "genreId")
    public List<BookDto> handleGetAllByGenreId(@RequestParam(value = "genreId") Long genreId) {
        return bookService.getAllByGenreId(genreId)
                .stream()
                .map(BookDto::transformDomainToDto)
                .toList();
    }

    @GetMapping(value = "/api/books/{id}")
    public BookDto handleGetById(@PathVariable(value = "id") long id) {
        var book = bookService.getById(id);
        return BookDto.transformDomainToDto(book);
    }

    @DeleteMapping(value = "/api/books/{id}")
    public void handleDelete(@PathVariable(value = "id") long id) {
        bookService.deleteById(id);
    }

    @DeleteMapping(value = "/api/books")
    public void handleDeleteAll() {
        bookService.deleteAll();
    }
}

