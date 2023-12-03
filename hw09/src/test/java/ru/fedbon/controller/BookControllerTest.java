package ru.fedbon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Sort;
import org.springframework.test.web.servlet.MockMvc;
import ru.fedbon.dto.NewBookDto;
import ru.fedbon.dto.UpdateBookDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Comment;
import ru.fedbon.model.Genre;
import ru.fedbon.service.AuthorService;
import ru.fedbon.service.BookService;
import ru.fedbon.service.CommentService;
import ru.fedbon.service.GenreService;

import java.util.List;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("Тест BookController должен")
@WebMvcTest(controllers = {BookController.class})
class BookControllerTest {

    @MockBean
    private BookService bookService;
    @MockBean
    private CommentService commentService;
    @MockBean
    private AuthorService authorService;
    @MockBean
    private GenreService genreService;

    @Autowired
    private MockMvc mockMvc;

    @DisplayName("отображать страницу со списком всех книг и указанием количества книг")
    @Test
    void shouldListAllBooksPage() throws Exception {
        var expectedFirstAuthor = new Author(1L, "firstAuthor");
        var expectedSecondAuthor = new Author(1L, "secondAuthor");
        var expectedFirstGenre = new Genre(1L, "firstGenre");
        var expectedSecondGenre = new Genre(1L, "secondGenre");

        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedFirstGenre, expectedFirstAuthor),
                new Book(2L, "secondBook", expectedSecondGenre, expectedSecondAuthor)
        );
        given(bookService.getAll(Sort.by(Sort.Direction.ASC, "id")))
                .willReturn(expectedBooks);
        given(genreService.getAll(Sort.by(Sort.Direction.ASC, "id")))
                .willReturn(List.of(expectedFirstGenre, expectedSecondGenre));
        given(authorService.getAll(Sort.by(Sort.Direction.ASC, "id")))
                .willReturn(List.of(expectedFirstAuthor, expectedSecondAuthor));

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(view().name("books"))
                .andExpect(model().attribute("books", expectedBooks))
                .andExpect(model().attribute("authors", List.of(expectedFirstAuthor, expectedSecondAuthor)))
                .andExpect(model().attribute("genres", List.of(expectedFirstGenre, expectedSecondGenre)));
        verify(bookService, times(1)).getAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(genreService, times(1)).getAll(Sort.by(Sort.Direction.ASC, "id"));
        verify(authorService, times(1)).getAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @DisplayName("добавить новую книгу в БД и затем отобразить страницу со списком всех книг")
    @Test
    void shouldCreateNewBookAndThenShouldListAllBooksPage() throws Exception {
        var expectedAuthor = new Author(1L, "firstAuthor");
        var expectedGenre = new Genre(1L, "firstGenre");

        when(bookService.create(any(NewBookDto.class))).thenReturn(new Book());

        mockMvc.perform(post("/create")
                        .param("title", "NewBookTitle")
                        .param("genreId", Long.toString(expectedGenre.getId()))
                        .param("authorId", Long.toString(expectedAuthor.getId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));

        verify(bookService, times(1)).create(any(NewBookDto.class));
    }

    @DisplayName("отображать страницу со всей информацией о конкретной книге, включая комментарии")
    @Test
    void shouldListEditPage() throws Exception {
        var expectedBook = new Book(1L, "firstBook", new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));
        var expectedComments = List.of(
                new Comment(1L, "firstComment", expectedBook),
                new Comment(1L, "firstComment", expectedBook)
        );
        given(bookService.getById(expectedBook.getId())).willReturn(expectedBook);
        given(commentService.getAllByBookId(expectedBook.getId())).willReturn(expectedComments);

        mockMvc.perform(get("/edit").param("id", Long.toString(expectedBook.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("edit"))
                .andExpect(model().attribute("book", expectedBook))
                .andExpect(model().attribute("comments", expectedComments));
        verify(bookService, times(1)).getById(expectedBook.getId());
        verify(commentService, times(1)).getAllByBookId(expectedBook.getId());
    }

    @DisplayName("изменить существующую книгу и затем отобразить страницу со списком всех книг")
    @Test
    void shouldUpdateBookAndThenShouldListAllBooksPage() throws Exception {
        var expectedAuthor = new Author(1L, "firstAuthor");
        var expectedGenre = new Genre(1L, "firstGenre");
        var expectedBook = new Book(1L, "firstBook", expectedGenre, expectedAuthor);

        when(bookService.update(any(UpdateBookDto.class))).thenReturn(expectedBook);

        mockMvc.perform(post("/update")
                        .param("id", Long.toString(expectedBook.getId()))
                        .param("title", "UpdatedTitle")
                        .param("genreId", Long.toString(expectedGenre.getId()))
                        .param("authorId", Long.toString(expectedAuthor.getId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));

        verify(bookService, times(1)).update(any(UpdateBookDto.class));
    }

    @DisplayName("отображать страницу со списком всех книг одного автора")
    @Test
    void shouldListBooksByAuthorPage() throws Exception {
        var expectedAuthor = new Author(1L, "firstAuthor");
        var expectedBooks = List.of(
                new Book(1L, "firstBook", new Genre(1L, "firstGenre"), expectedAuthor),
                new Book(2L, "secondBook", new Genre(2L, "secondGenre"), expectedAuthor)
        );
        given(bookService.getAllByAuthorId(expectedAuthor.getId())).willReturn(expectedBooks);

        mockMvc.perform(get("/books-by-author").param("id", String.valueOf(expectedAuthor.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("books-by-author"))
                .andExpect(model().attribute("books", expectedBooks));
        verify(bookService, times(1)).getAllByAuthorId(expectedAuthor.getId());
    }

    @DisplayName("отображать страницу со списком всех книг одного жанра")
    @Test
    void shouldListBooksByGenrePage() throws Exception {
        var expectedGenre = new Genre(1L, "firstGenre");
        var expectedBooks = List.of(
                new Book(1L, "firstBook", expectedGenre, new Author(1L, "firstAuthor")),
                new Book(2L, "secondBook", expectedGenre, new Author(2L, "secondAuthor"))
        );
        given(bookService.getAllByGenreId(expectedGenre.getId())).willReturn(expectedBooks);

        mockMvc.perform(get("/books-by-genre").param("id", String.valueOf(expectedGenre.getId())))
                .andExpect(status().isOk())
                .andExpect(view().name("books-by-genre"))
                .andExpect(model().attribute("books", expectedBooks));
        verify(bookService, times(1)).getAllByGenreId(expectedGenre.getId());
    }

    @DisplayName("удалить книгу по ее идентификатору и затем отобразить страницу со списком всех книг")
    @Test
    void shouldDeleteBookByIdAndThenShouldListALLBooksPage() throws Exception {
        var expectedBook = new Book(1L, "firstBook", new Genre(1L, "firstGenre"),
                new Author(1L, "firstAuthor"));
        mockMvc.perform(post("/delete").param("id", Long.toString(expectedBook.getId())))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(bookService, times(1)).deleteById(expectedBook.getId());
    }

    @DisplayName("удалить все книги и затем отобразить страницу со списком всех книг")
    @Test
    void shouldDeleteAllBooksAndThenShouldListAllBooksPage() throws Exception {
        mockMvc.perform(post("/delete-all"))
                .andExpect(status().isFound())
                .andExpect(view().name("redirect:/books"));
        verify(bookService, times(1)).deleteAll();
    }
}