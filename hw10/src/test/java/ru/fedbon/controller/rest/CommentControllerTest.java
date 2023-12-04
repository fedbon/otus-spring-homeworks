package ru.fedbon.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.controller.CommentController;
import ru.fedbon.dto.CommentDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.model.Genre;
import ru.fedbon.service.CommentService;


import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест CommentController должен")
@WebMvcTest(controllers = {CommentController.class})
class CommentControllerTest {

    @MockBean
    private CommentService commentService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("возвращать корректный список комментариев для конкретной книги")
    @Test
    void shouldReturnCorrectCommentsListForSpecificBook() throws Exception {
        var expectedBook = new Book(1L, "firstBook", new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));
        var expectedComments = List.of(
                new Comment(1L, "firstComment", expectedBook),
                new Comment(1L, "firstComment", expectedBook)
        );

        given(commentService.getAllByBookId(expectedBook.getId())).willReturn(expectedComments);
        List<CommentDto> expectedResult = expectedComments.stream()
                .map(CommentDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/books/{id}/comments", expectedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(commentService, times(1)).getAllByBookId(expectedBook.getId());
    }
}