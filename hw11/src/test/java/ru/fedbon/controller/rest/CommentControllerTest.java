package ru.fedbon.controller.rest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.fedbon.controller.CommentController;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.CommentRepository;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест CommentController должен")
@WebFluxTest(CommentController.class)
class CommentControllerTest {

    private final Author author = new Author("1", "firstAuthor");

    private final Genre genre = new Genre("1", "firstGenre");

    private final Book expectedBook = new Book("1", "Book Title", genre, author);

    private final List<Comment> expectedComments = List.of(
            new Comment("1", "firstComment", expectedBook),
            new Comment("2", "secondComment", expectedBook)
    );

    private final List<CommentDto> expectedCommentsDto = List.of(
            new CommentDto("1", "firstComment", "1"),
            new CommentDto("2", "secondComment", "1")
    );

    @Mock
    private BookRepository bookRepository;

    @Mock
    private CommentRepository commentRepository;

    @InjectMocks
    private CommentController commentController;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(commentController).build();
    }

    @DisplayName("возвращать корректный список комментариев для конкретной книги")
    @Test
    void shouldReturnCorrectCommentsListByBookId() {
        given(bookRepository.findById(expectedBook.getId()))
                .willReturn(Mono.just(expectedBook));
        given(commentRepository.findAllByBook(any()))
                .willReturn(Flux.fromIterable(expectedComments));

        webTestClient.get()
                .uri("/api/books/{id}/comments", expectedBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .value(commentDtos -> Assertions.assertThat(commentDtos)
                        .hasSize(2)
                        .containsExactlyInAnyOrderElementsOf(expectedCommentsDto));

        verify(bookRepository, times(1)).findById(expectedBook.getId());
        verify(commentRepository, times(1)).findAllByBook(any());
    }
}
