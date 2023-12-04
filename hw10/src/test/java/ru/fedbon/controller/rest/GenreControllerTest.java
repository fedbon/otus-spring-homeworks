package ru.fedbon.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.controller.GenreController;
import ru.fedbon.dto.GenreDto;
import ru.fedbon.model.Genre;
import ru.fedbon.service.GenreService;


import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест GenreController должен")
@WebMvcTest(controllers = {GenreController.class})
class GenreControllerTest {

    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("возвращать корректный список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() throws Exception {
        var expectedGenres = List.of(
                new Genre(1L, "firstGenre"),
                new Genre(2L, "secondGenre")
        );
        given(genreService.getAll(Sort.by(Sort.Direction.ASC,"id"))).willReturn(expectedGenres);

        List<GenreDto> expectedResult = expectedGenres.stream()
                .map(GenreDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/genres"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(genreService, times(1)).getAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}