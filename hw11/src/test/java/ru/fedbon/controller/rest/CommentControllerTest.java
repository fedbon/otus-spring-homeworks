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
import ru.fedbon.controller.CommentController;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.service.CommentService;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест CommentController должен")
@WebFluxTest(CommentController.class)
class CommentControllerTest {

    @Mock
    private CommentService commentService;

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
        var expectedBookId = "1";
        var expectedCommentsDto = List.of(
                new CommentDto("1", "firstComment", "1"),
                new CommentDto("2", "secondComment", "1")
        );
        given(commentService.getAllByBookId(eq(expectedBookId), any()))
                .willReturn(Flux.fromIterable(expectedCommentsDto));

        webTestClient.get()
                .uri("/api/books/{id}/comments", expectedBookId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .value(commentDtos -> {
                    Assertions.assertThat(commentDtos)
                            .hasSize(2)
                            .containsExactlyInAnyOrderElementsOf(expectedCommentsDto);
                });

        verify(commentService, times(1)).getAllByBookId(eq(expectedBookId), any());
    }
}
