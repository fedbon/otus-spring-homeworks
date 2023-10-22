package ru.fedbon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fedbon.dto.BookDto;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.AuthorRepository;
import ru.fedbon.repository.BookRepository;
import ru.fedbon.repository.GenreRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.BDDMockito.*;

@DisplayName("Тест BookServiceImpl должен")
@SpringBootTest
class BookServiceImplTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;

    private static final long EXPECTED_DELETED_BOOKS_COUNT = 3;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorRepository authorRepository;

    @MockBean
    private GenreRepository genreRepository;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBooksCount() {
        given(bookRepository.count()).willReturn(EXPECTED_BOOKS_COUNT);
        var actualBooksCount = bookService.getCount();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
        verify(bookRepository, times(1)).count();
    }

    @Test
    @DisplayName("добавлять книгу в БД при указании ее названия и идентификаторов жанра и автора")
    void shouldAddBookWithTitleAndIdentifiers() {
        var genreId = 1L;
        var authorId = 2L;

        var bookDto = new BookDto();
        bookDto.setTitle("Книг_01");
        bookDto.setGenreId(genreId);
        bookDto.setAuthorId(authorId);

        var genre = new Genre();
        genre.setId(genreId);

        var author = new Author();
        author.setId(authorId);

        when(genreRepository.findById(genreId)).thenReturn(Optional.of(genre));
        when(authorRepository.findById(authorId)).thenReturn(Optional.of(author));

        var addedBookDto = bookService.add(bookDto);

        verify(genreRepository, times(1)).findById(genreId);
        verify(authorRepository, times(1)).findById(authorId);
        verify(bookRepository, times(1)).insert(any(Book.class));

        assertNotNull(addedBookDto);
        assertEquals(bookDto.getTitle(), addedBookDto.getTitle());
        assertEquals(bookDto.getGenreId(), addedBookDto.getGenreId());
        assertEquals(bookDto.getAuthorId(), addedBookDto.getAuthorId());
    }

    @Test
    @DisplayName("изменять имеющуюся в БД книгу при указании ее id, названия и идентификаторов жанра и автора")
    void shouldChangeBookWithTitleAndIdentifiers() {
        var bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setTitle("Книга_01");
        bookDto.setGenreId(1L);
        bookDto.setAuthorId(1L);

        var genre = new Genre(1L, "Жанр_01");
        var author = new Author(1L, "Автор_02");
        var book = new Book(1L, "Книга_02", genre, author);

        when(genreRepository.findById(1L)).thenReturn(Optional.of(genre));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.change(bookDto);

        verify(genreRepository, times(1)).findById(1L);
        verify(authorRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).update(any(Book.class));
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг")
    void shouldReturnExpectedBooksList() {
        var expectedBooks = List.of(
                new Book(1L, "Книга_01", new Genre(1L, "Жанр_01"),
                        new Author(3L, "Автор_03")),
                new Book(2L, "Книга_02", new Genre(2L, "Жанр_02"),
                        new Author(1L, "Автор_01")),
                new Book(3L, "Книга_03", new Genre(1L, "Жанр_01"),
                        new Author(2L, "Автор_02"))
        );

        given(bookRepository.findAll()).willReturn(expectedBooks);
        var actualBooksList = bookService.getAll();

        assertThat(actualBooksList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного жанра")
    void shouldReturnBooksListByExpectedGenre() {
        var expectedGenre = new Genre(1L, "Жанр_01");
        var expectedBooksByGenre = List.of(
                new Book(1L, "Книга_01", expectedGenre, new Author(3L, "Автор_03")),
                new Book(3L, "Книга_03", expectedGenre, new Author(2L, "Автор_02"))
        );

        given(bookRepository.findAllByGenre(expectedGenre.getGenreName())).willReturn(expectedBooksByGenre);
        var actualBooksByGenre = bookService.getAllByGenreName(expectedGenre.getGenreName());

        assertThat(actualBooksByGenre)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooksByGenre);
        verify(bookRepository, times(1)).findAllByGenre(expectedGenre.getGenreName());
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного автора")
    void shouldReturnBooksListByExpectedAuthor() {
        var expectedAuthor = new Author(1L, "Автор_01");
        var expectedBooksByAuthor = List.of(new Book(2L, "Книга_02",
                new Genre(2L, "Жанр_02"), expectedAuthor));

        given(bookRepository.findAllByAuthor(expectedAuthor.getName())).willReturn(expectedBooksByAuthor);
        var actualBooksByAuthor = bookService.getAllByAuthorName(expectedAuthor.getName());

        assertThat(actualBooksByAuthor)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooksByAuthor);
        verify(bookRepository, times(1)).findAllByAuthor(expectedAuthor.getName());
    }

    @Test
    @DisplayName("удалять заданную книгу по ее идентификатору")
    void shouldDeleteBookById() {
        long bookId = 1L;

        bookService.deleteById(bookId);

        verify(bookRepository, times(1)).deleteById(bookId);

    }

    @Test
    @DisplayName("удалять все книги из БД")
    void shouldDeleteAllBooks() {
        given(bookRepository.deleteAll()).willReturn(EXPECTED_DELETED_BOOKS_COUNT);
        var actualDeletedBooksCount = bookService.deleteAll();

        assertThat(actualDeletedBooksCount).isEqualTo(EXPECTED_DELETED_BOOKS_COUNT);
        verify(bookRepository, times(1)).deleteAll();
    }
}