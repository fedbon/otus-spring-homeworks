package ru.fedbon.controller.rest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.service.AuthorService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест AuthorController должен")
@SpringBootTest
@AutoConfigureWebTestClient
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("возвращать корректный список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() throws JsonProcessingException {
        var expectedAuthorsDto = List.of(
                new AuthorDto("1", "firstAuthor"),
                new AuthorDto("2", "secondAuthor")
        );

        given(authorService.getAll(any())).willReturn(Flux.fromIterable(expectedAuthorsDto));

        webTestClient.get().uri("/api/authors")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(expectedAuthorsDto));

        verify(authorService, times(1)).getAll(any());
    }
}
