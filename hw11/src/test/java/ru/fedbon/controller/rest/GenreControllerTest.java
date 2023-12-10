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
import ru.fedbon.dto.GenreDto;
import ru.fedbon.service.GenreService;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@DisplayName("Тест GenreController должен")
@SpringBootTest
@AutoConfigureWebTestClient
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private WebTestClient webTestClient;

    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("возвращать корректный список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() throws JsonProcessingException {
        var expectedGenresDto = List.of(
                new GenreDto("1", "firstGenre"),
                new GenreDto("2", "secondGenre")
        );

        given(genreService.getAll(any())).willReturn(Flux.fromIterable(expectedGenresDto));

        webTestClient.get().uri("/api/genres")
                .header(HttpHeaders.ACCEPT, "application/json")
                .exchange()
                .expectStatus().isOk()
                .expectBody().json(objectMapper.writeValueAsString(expectedGenresDto));

        verify(genreService, times(1)).getAll(any());
    }
}