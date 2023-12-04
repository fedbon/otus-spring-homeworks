package ru.fedbon.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.controller.BookController;
import ru.fedbon.dto.BookDto;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.dto.UpdateBookDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.service.BookService;


import java.util.List;
import java.util.stream.Collectors;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("Тест BookController должен")
@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

    @MockBean
    private BookService bookService;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @DisplayName("сохранять новую книгу")
    @Test
    void shouldCreateNewBook() throws Exception {
        var expectedBook = new Book(1L, "firstBook",
                new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));

        given(bookService.create(any(NewBookDto.class))).willReturn(expectedBook);
        var expectedResult = objectMapper.writeValueAsString(BookDto.transformDomainToDto(expectedBook));

        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
        verify(bookService, times(1)).create(any(NewBookDto.class));
    }

    @DisplayName("обновлять книгу по идентификатору")
    @Test
    void shouldUpdateBookById() throws Exception {
        var expectedBook = new Book(1L, "firstBook",
                new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));
        given(bookService.update(any(UpdateBookDto.class))).willReturn(expectedBook);
        var expectedResult = objectMapper.writeValueAsString(BookDto.transformDomainToDto(expectedBook));

        mockMvc.perform(put("/api/books/{id}", expectedBook.getId())
                        .contentType(APPLICATION_JSON)
                        .content(expectedResult))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));
        verify(bookService, times(1)).update(any(UpdateBookDto.class));
    }

    @DisplayName("возвращать корректный список всех книг")
    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        var expectedFirstAuthor = new Author(1L, "firstAuthor");
        var expectedSecondAuthor = new Author(1L, "secondAuthor");
        var expectedFirstGenre = new Genre(1L, "firstGenre");
        var expectedSecondGenre = new Genre(1L, "secondGenre");

        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedFirstGenre, expectedFirstAuthor),
                new Book(2L, "secondBook", expectedSecondGenre, expectedSecondAuthor)
        );

        given(bookService.getAll(Sort.by(Sort.Direction.ASC,"id"))).willReturn(expectedBooks);
        List<BookDto> expectedResult = expectedBooks.stream()
                .map(BookDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(bookService, times(1)).getAll(Sort.by(Sort.Direction.ASC,"id"));
    }

    @DisplayName("возвращать корректный список всех книг конкретного автора")
    @Test
    void shouldReturnCorrectBooksListByAuthor() throws Exception {
        var expectedAuthor = new Author(1L, "firstAuthor");
        var expectedFirstGenre = new Genre(1L, "firstGenre");
        var expectedSecondGenre = new Genre(2L, "secondGenre");
        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedFirstGenre, expectedAuthor),
                new Book(2L, "secondBook", expectedSecondGenre, expectedAuthor)
        );

        given(bookService.getAllByAuthorId(expectedAuthor.getId())).willReturn(expectedBooks);
        List<BookDto> expectedResult = expectedBooks.stream()
                .map(BookDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/books").param("authorId", String.valueOf(expectedAuthor.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(bookService, times(1)).getAllByAuthorId(expectedAuthor.getId());
    }

    @DisplayName("возвращать корректный список всех книг конкретного жанра")
    @Test
    void shouldReturnCorrectBooksListByGenre() throws Exception {
        var expectedGenre = new Genre(1L, "firstGenre");
        var expectedFirstAuthor = new Author(1L, "firstAuthor");
        var expectedSecondAuthor = new Author(2L, "secondAuthor");
        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedGenre, expectedFirstAuthor),
                new Book(2L, "secondBook", expectedGenre, expectedSecondAuthor)
        );

        given(bookService.getAllByGenreId(expectedGenre.getId())).willReturn(expectedBooks);
        List<BookDto> expectedResult = expectedBooks.stream()
                .map(BookDto::transformDomainToDto)
                .collect(Collectors.toList());

        mockMvc.perform(get("/api/books").param("genreId", String.valueOf(expectedGenre.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(bookService, times(1)).getAllByGenreId(expectedGenre.getId());
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldReturnById() throws Exception {
        var expectedBook = new Book(1L, "firstBook",
                new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));

        given(bookService.getById(expectedBook.getId())).willReturn(expectedBook);
        var expectedResult = BookDto.transformDomainToDto(expectedBook);

        mockMvc.perform(get("/api/books/{id}", expectedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedResult)));
        verify(bookService, times(1)).getById(expectedBook.getId());
    }

    @DisplayName("удалять книгу по идентификатору")
    @Test
    void shouldDeleteById() throws Exception {
        mockMvc.perform(delete("/api/books/{id}", 1L))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteById(1L);
    }

    @DisplayName("удалять все книги")
    @Test
    void shouldDeleteAll() throws Exception {
        mockMvc.perform(delete("/api/books"))
                .andExpect(status().isOk());

        verify(bookService, times(1)).deleteAll();
    }
}