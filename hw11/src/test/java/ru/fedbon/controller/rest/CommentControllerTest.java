package ru.fedbon.controller.rest;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.service.CommentService;


import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;



@DisplayName("Тест CommentController должен")
@SpringBootTest
@AutoConfigureWebTestClient
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private WebTestClient webTestClient;

    @DisplayName("возвращать корректный список комментариев для конкретной книги")
    @Test
    void shouldReturnCorrectCommentsListByBookId() {
        var expectedBook = new Book("1", "firstBook", new Genre("1", "firstGenre"),
                new Author("1", "firstAuthor"));
        var expectedCommentsDto = List.of(
                new CommentDto("1", "firstComment", "1"),
                new CommentDto("2", "secondComment", "1")
        );

        given(commentService.getAllByBookId(eq(expectedBook.getId()), any()))
                .willReturn(Flux.fromIterable(expectedCommentsDto));

        webTestClient.get().uri("/api/books/{id}/comments", expectedBook.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CommentDto.class)
                .value(expectedDto -> assertThat(expectedCommentsDto,
                        containsInAnyOrder(expectedCommentsDto.toArray())));

        verify(commentService, times(1)).getAllByBookId(eq(expectedBook.getId()), any());
    }
}