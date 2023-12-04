package ru.fedbon.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.controller.AuthorController;
import ru.fedbon.dto.AuthorDto;
import ru.fedbon.model.Author;
import ru.fedbon.service.AuthorService;


import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест AuthorController должен")
@WebMvcTest(controllers = {AuthorController.class})
class AuthorControllerTest {

    @MockBean
    private AuthorService authorService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("возвращать корректный список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() throws Exception {
        var expectedAuthors = List.of(
                new Author(1L, "firstAuthor"),
                new Author(2L, "secondAuthor")
        );
        given(authorService.getAll(Sort.by(Sort.Direction.ASC,"id"))).willReturn(expectedAuthors);

        List<AuthorDto> expectedResult = expectedAuthors.stream()
                .map(AuthorDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/authors"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(authorService, times(1)).getAll(Sort.by(Sort.Direction.ASC,"id"));
    }
}