package ru.fedbon.controller.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.controller.AuthorController;
import ru.fedbon.service.AuthorService;


import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;


@DisplayName("Тест AuthorController должен")
@WebFluxTest(AuthorController.class)
class AuthorControllerTest {

    private final List<AuthorDto> authors = List.of(
            new AuthorDto("1", "Author1"),
            new AuthorDto("2", "Author2")
    );

    @Mock
    private AuthorService authorService;

    @InjectMocks
    private AuthorController authorController;

    @Autowired
    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToController(authorController).build();
    }

    @DisplayName("возвращать корректный список всех авторов")
    @Test
    void testHandleGetAll() {
        given(authorService.getAll(any(Sort.class))).willReturn(Flux.fromIterable(authors));

        webTestClient.get().uri("/api/authors").exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .hasSize(2)
                .isEqualTo(authors);

        verify(authorService).getAll(any(Sort.class));
    }
}
