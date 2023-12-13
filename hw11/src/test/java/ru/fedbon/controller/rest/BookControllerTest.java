package ru.fedbon.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.controller.BookController;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.service.BookService;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест BookController должен")
@WebFluxTest(BookController.class)
class BookControllerTest {

    private final List<BookDto> books = List.of(
            new BookDto("1", "firstBook", "firstAuthor", "firstGenre"),
            new BookDto("2", "secondBook", "secondAuthor", "secondGenre")
    );

    private final BookCreateDto newBook =
            new BookCreateDto("firstBook", "firstAuthor", "firstGenre");

    private final BookUpdateDto updatedBook =
            new BookUpdateDto("1", "firstBook", "firstAuthor", "firstGenre");

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(bookController).build();
    }

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldCreateNewBook() {
        given(bookService.create(any(BookCreateDto.class))).willReturn(Mono.just(books.get(0)));

        webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newBook))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .isEqualTo(books.get(0));

        verify(bookService, times(1)).create(any(BookCreateDto.class));
    }

    @DisplayName("обновлять книгу по идентификатору")
    @Test
    void shouldUpdateBookById() {
        given(bookService.update(any(BookUpdateDto.class))).willReturn(Mono.just(books.get(0)));

        webTestClient.put().uri("/api/books/{id}", books.get(0).getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(books.get(0));

        verify(bookService, times(1)).update(any(BookUpdateDto.class));
    }

    @DisplayName("возвращать корректный список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        given(bookService.getAll(any(Sort.class))).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(2)
                .isEqualTo(books);

        verify(bookService, times(1)).getAll(any(Sort.class));
    }

    @DisplayName("возвращать корректный список всех книг конкретного автора")
    @Test
    void shouldReturnCorrectBooksListByAuthor() {
        var authorId = "1";
        given(bookService.getAllByAuthorId(authorId)).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books?authorId={id}", authorId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(books);

        verify(bookService, times(1)).getAllByAuthorId(authorId);
    }

    @DisplayName("возвращать корректный список всех книг конкретного жанра")
    @Test
    void shouldReturnCorrectBooksListByGenre() {
        var genreId = "1";
        given(bookService.getAllByGenreId(genreId)).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books?genreId={id}", genreId)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(books);

        verify(bookService, times(1)).getAllByGenreId(genreId);
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldReturnById() {
        var bookId = "1";
        given(bookService.getById(bookId)).willReturn(Mono.just(books.get(0)));

        webTestClient.get().uri("/api/books/{id}", bookId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(books.get(0));

        verify(bookService, times(1)).getById(bookId);
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDeleteById() {
        var bookId = "1";

        Mockito.when(bookService.deleteById(bookId)).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/books/{id}", bookId)
                .exchange()
                .expectStatus().isNoContent();

        verify(bookService, times(1)).deleteById(bookId);
    }

    @DisplayName("удалять все книги")
    @Test
    void shouldDeleteAll() {
        Mockito.when(bookService.deleteAll()).thenReturn(Mono.empty());

        webTestClient.delete().uri("/api/books")
                .exchange()
                .expectStatus().isNoContent();

        verify(bookService, times(1)).deleteAll();
    }
}