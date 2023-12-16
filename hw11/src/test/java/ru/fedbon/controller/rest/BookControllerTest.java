package ru.fedbon.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
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
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;


import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест BookController должен")
@WebFluxTest(BookController.class)
class BookControllerTest {

    private final Author author = new Author("1", "firstAuthor");

    private final Genre genre = new Genre("1", "firstGenre");

    private final List<Book> books = List.of(
            new Book("1", "firstBook", genre, author),
            new Book("2", "secondBook", genre, author)
    );

    private final BookCreateDto newBook =
            new BookCreateDto("firstBook", "1", "1");

    private final BookUpdateDto updatedBook =
            new BookUpdateDto("1", "firstBook", "1", "1");

    private final List<BookDto> bookDtos = List.of(
            new BookDto("1", "firstBook", "firstAuthor", "firstGenre"),
            new BookDto("2", "secondBook", "firstAuthor", "firstGenre"));

    @Mock
    private BookRepository bookRepository;

    @Mock
    private GenreRepository genreRepository;

    @Mock
    private AuthorRepository authorRepository;

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
        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));
        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(bookRepository.save(any(Book.class))).willReturn(Mono.just(books.get(0)));

        webTestClient.post().uri("/api/books")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(newBook))
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        verify(genreRepository, times(1)).findById(anyString());
        verify(authorRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("обновлять книгу по идентификатору")
    @Test
    void shouldUpdateBookById() {
        given(genreRepository.findById(anyString())).willReturn(Mono.just(genre));
        given(authorRepository.findById(anyString())).willReturn(Mono.just(author));
        given(bookRepository.findById(anyString())).willReturn(Mono.just(books.get(0)));
        given(bookRepository.save(any(Book.class))).willReturn(Mono.just(books.get(0)));

        webTestClient.put().uri("/api/books/{id}", updatedBook.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedBook))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        verify(genreRepository, times(1)).findById(anyString());
        verify(authorRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).findById(anyString());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @DisplayName("возвращать корректный список всех книг")
    @Test
    void shouldReturnCorrectBooksList() {
        given(bookRepository.findAll(any(Sort.class))).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .hasSize(2)
                .isEqualTo(bookDtos);

        verify(bookRepository, times(1)).findAll(any(Sort.class));
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldReturnById() {
        given(bookRepository.findById(anyString())).willReturn(Mono.just(books.get(0)));

        webTestClient.get().uri("/api/books/{id}", books.get(0))
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .returnResult().getResponseBody();

        verify(bookRepository, times(1)).findById(anyString());
    }

    @DisplayName("возвращать корректный список всех книг конкретного автора")
    @Test
    void shouldReturnCorrectBooksListByAuthor() {
        given(authorRepository.findById(author.getId())).willReturn(Mono.just(author));
        given(bookRepository.findAllByAuthorId(author.getId())).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books?authorId={id}", author.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(bookDtos);

        verify(authorRepository, times(1)).findById(author.getId());
        verify(bookRepository, times(1)).findAllByAuthorId(author.getId());
    }

    @DisplayName("возвращать корректный список всех книг конкретного жанра")
    @Test
    void shouldReturnCorrectBooksListByGenre() {
        given(genreRepository.findById(genre.getId())).willReturn(Mono.just(genre));
        given(bookRepository.findAllByGenreId(genre.getId())).willReturn(Flux.fromIterable(books));

        webTestClient.get().uri("/api/books?genreId={id}", genre.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .isEqualTo(bookDtos);

        verify(genreRepository, times(1)).findById(genre.getId());
        verify(bookRepository, times(1)).findAllByGenreId(genre.getId());
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDeleteById() {
        given(bookRepository.deleteById(books.get(0).getId())).willReturn(Mono.empty());

        webTestClient.delete().uri("/api/books/{id}", books.get(0).getId())
                .exchange()
                .expectStatus().isNoContent();

        verify(bookRepository, times(1)).deleteById(books.get(0).getId());
    }

    @DisplayName("удалять все книги")
    @Test
    void shouldDeleteAll() {
        given(bookRepository.deleteAll()).willReturn(Mono.empty());

        webTestClient.delete().uri("/api/books")
                .exchange()
                .expectStatus().isNoContent();

        verify(bookRepository, times(1)).deleteAll();
    }
}