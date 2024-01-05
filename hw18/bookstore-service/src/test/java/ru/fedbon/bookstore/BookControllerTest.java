package ru.fedbon.bookstore;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.bookstore.controller.BookController;
import ru.fedbon.bookstore.dto.BookCreateDto;
import ru.fedbon.bookstore.dto.BookUpdateDto;
import ru.fedbon.bookstore.mapper.BookMapper;
import ru.fedbon.bookstore.model.Author;
import ru.fedbon.bookstore.model.Book;
import ru.fedbon.bookstore.model.Genre;
import ru.fedbon.bookstore.service.BookService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
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

        var bookCreateDto = new BookCreateDto();
        bookCreateDto.setTitle("firstBook");
        bookCreateDto.setAuthorId(1L);
        bookCreateDto.setGenreId(1L);

        var expectedBookDto = BookMapper.mapBookToDto(expectedBook);

        given(bookService.create(any(BookCreateDto.class))).willReturn(expectedBookDto);
        var expectedResult = objectMapper.writeValueAsString(expectedBookDto);

        mockMvc.perform(post("/api/books")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookCreateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(bookService, times(1)).create(any(BookCreateDto.class));
    }

    @DisplayName("обновлять книгу по идентификатору")
    @Test
    void shouldUpdateBookById() throws Exception {
        var expectedBook = new Book(1L, "firstBook",
                new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));

        var bookUpdateDto = new BookUpdateDto();
        bookUpdateDto.setId(1L);
        bookUpdateDto.setTitle("firstBook");
        bookUpdateDto.setAuthorId(1L);
        bookUpdateDto.setGenreId(1L);

        var expectedBookDto = BookMapper.mapBookToDto(expectedBook);

        given(bookService.update(any(BookUpdateDto.class))).willReturn(expectedBookDto);
        var expectedResult = objectMapper.writeValueAsString(expectedBookDto);

        mockMvc.perform(put("/api/books/{id}", expectedBook.getId())
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookUpdateDto)))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedResult));

        verify(bookService, times(1)).update(any(BookUpdateDto.class));
    }

    @DisplayName("возвращать корректный список всех книг")
    @Test
    void shouldReturnCorrectBooksList() throws Exception {
        var expectedFirstAuthor = new Author(1L, "firstAuthor");
        var expectedSecondAuthor = new Author(2L, "secondAuthor");
        var expectedFirstGenre = new Genre(1L, "firstGenre");
        var expectedSecondGenre = new Genre(2L, "secondGenre");

        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedFirstGenre, expectedFirstAuthor),
                new Book(2L, "secondBook", expectedSecondGenre, expectedSecondAuthor)
        );

        var expectedBooksDto = expectedBooks.stream()
                .map(BookMapper::mapBookToDto)
                .toList();

        given(bookService.getAll(Sort.by(Sort.Direction.ASC,"id"))).willReturn(expectedBooksDto);

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBooksDto)));

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

        var expectedBooksDto = expectedBooks.stream()
                .map(BookMapper::mapBookToDto)
                .toList();

        given(bookService.getAllByAuthorId(expectedAuthor.getId())).willReturn(expectedBooksDto);

        mockMvc.perform(get("/api/books").param("authorId", String.valueOf(expectedAuthor.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBooksDto)));

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

        var expectedBooksDto = expectedBooks.stream()
                .map(BookMapper::mapBookToDto)
                .toList();

        given(bookService.getAllByGenreId(expectedGenre.getId())).willReturn(expectedBooksDto);

        mockMvc.perform(get("/api/books").param("genreId", String.valueOf(expectedGenre.getId())))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBooksDto)));

        verify(bookService, times(1)).getAllByGenreId(expectedGenre.getId());
    }

    @DisplayName("возвращать книгу по идентификатору")
    @Test
    void shouldReturnById() throws Exception {
        var expectedBook = new Book(1L, "firstBook",
                new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));

        var expectedBookDto = BookMapper.mapBookToDto(expectedBook);

        given(bookService.getById(expectedBook.getId())).willReturn(expectedBookDto);

        mockMvc.perform(get("/api/books/{id}", expectedBook.getId()))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(expectedBookDto)));

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