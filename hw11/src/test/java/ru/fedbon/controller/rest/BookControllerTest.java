package ru.fedbon.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.BookCreateDto;
import ru.fedbon.dto.BookUpdateDto;
import ru.fedbon.mapper.BookMapper;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.service.BookService;


import java.util.List;

import static org.bson.assertions.Assertions.assertNotNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест BookController должен")
@SpringBootTest
@AutoConfigureWebTestClient
class BookControllerTest {

    private final Author firstAuthor = new Author("1", "firstAuthor");

    private final Author secondAuthor = new Author("2", "secondAuthor");

    private final Genre firstGenre = new Genre("1", "firstGenre");

    private final Genre secondGenre = new Genre("2", "secondGenre");

    private final Book firstBook = new Book("1", "firstBook", firstGenre, firstAuthor);

    private final Book secondBook = new Book("2", "secondBook", secondGenre, secondAuthor);

    private final BookDto firstBookDto = BookMapper.mapBookToDto(firstBook);

    private final BookDto secondBookDto = BookMapper.mapBookToDto(secondBook);

    @MockBean
    private BookService bookService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldCreateNewBook() {
        given(bookService.create(any(BookCreateDto.class))).willReturn(Mono.just(firstBookDto));

        webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(firstBookDto))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .value(actualBookDto -> {
                    assertNotNull(actualBookDto.getId());
                    assertEquals(firstBookDto.getTitle(), actualBookDto.getTitle());
                    assertEquals(firstBookDto.getAuthor(), actualBookDto.getAuthor());
                    assertEquals(firstBookDto.getGenre(), actualBookDto.getGenre());
                });

        verify(bookService, times(1)).create(any(BookCreateDto.class));
    }

    @DisplayName("обновлять книгу по идентификатору")
    @Test
    void shouldUpdateBookById() {
        given(bookService.update(any(BookUpdateDto.class))).willReturn(Mono.just(firstBookDto));

        webTestClient.put().uri("/api/books/{id}", firstBookDto.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(firstBookDto))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(actualBookDto -> {
                    assertNotNull(actualBookDto.getId());
                    assertEquals(firstBookDto.getTitle(), actualBookDto.getTitle());
                    assertEquals(firstBookDto.getAuthor(), actualBookDto.getAuthor());
                    assertEquals(firstBookDto.getGenre(), actualBookDto.getGenre());
                });

        verify(bookService, times(1)).update(any(BookUpdateDto.class));
    }

    @DisplayName("возвращать корректный список всех книг")
    @Test
    void shouldReturnCorrectBooksList() throws JsonProcessingException {
        given(bookService.getAll(any(Sort.class)))
                .willReturn(Flux.fromIterable(List.of(firstBookDto, secondBookDto)));

        webTestClient.get().uri("/api/books")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(List.of(firstBookDto, secondBookDto)));

        verify(bookService, times(1)).getAll(any(Sort.class));
    }

    @DisplayName("возвращать корректный список всех книг конкретного автора")
    @Test
    void shouldReturnCorrectBooksListByAuthor() throws JsonProcessingException {
        given(bookService.getAllByAuthorId(firstAuthor.getId()))
                .willReturn(Flux.fromIterable(List.of(firstBookDto, secondBookDto)));

        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParam("authorId", firstAuthor.getId()).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(List.of(firstBookDto, secondBookDto)));

        verify(bookService, times(1)).getAllByAuthorId(firstAuthor.getId());
    }

    @DisplayName("возвращать корректный список всех книг конкретного жанра")
    @Test
    void shouldReturnCorrectBooksListByGenre() throws JsonProcessingException {
        given(bookService.getAllByGenreId(firstGenre.getId()))
                .willReturn(Flux.fromIterable(List.of(firstBookDto, secondBookDto)));

        webTestClient.get().uri(uriBuilder -> uriBuilder.path("/api/books")
                        .queryParam("genreId", firstGenre.getId()).build())
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(List.of(firstBookDto, secondBookDto)));

        verify(bookService, times(1)).getAllByGenreId(firstGenre.getId());
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldReturnById() {
        given(bookService.getById(firstBook.getId())).willReturn(Mono.just(firstBookDto));

        webTestClient.get().uri("/api/books/{id}", firstBook.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(actualBookDto -> {
                    assertNotNull(actualBookDto.getId());
                    assertEquals(firstBookDto.getTitle(), actualBookDto.getTitle());
                    assertEquals(firstBookDto.getAuthor(), actualBookDto.getAuthor());
                    assertEquals(firstBookDto.getGenre(), actualBookDto.getGenre());
                });

        verify(bookService, times(1)).getById(firstBook.getId());
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDeleteById() {
        webTestClient.delete().uri("/api/books/{id}", "1")
                .exchange()
                .expectStatus().isOk();

        verify(bookService, times(1)).deleteById("1");
    }

    @DisplayName("удалять все книги")
    @Test
    void shouldDeleteAll() {
        webTestClient.delete().uri("/api/books")
                .exchange()
                .expectStatus().isOk();

        verify(bookService, times(1)).deleteAll();
    }
}