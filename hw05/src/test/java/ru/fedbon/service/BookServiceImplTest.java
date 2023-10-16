package ru.fedbon.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;
import ru.fedbon.repository.BookRepository;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;

@DisplayName("Тест BookServiceImpl должен")
@SpringBootTest
class BookServiceImplTest {

    private static final long EXPECTED_BOOKS_COUNT = 3;

    private static final long EXPECTED_DELETED_BOOKS_COUNT = 3;

    private static final long EXISTING_BOOK_ID = 1;

    private static final long NOT_EXISTING_BOOK_ID = 100;

    @MockBean
    private BookRepository bookRepository;

    @MockBean
    private AuthorService authorService;

    @MockBean
    private GenreService genreService;

    @Autowired
    private BookServiceImpl bookService;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBooksCount() {
        given(bookRepository.count()).willReturn(EXPECTED_BOOKS_COUNT);
        var actualBooksCount = bookService.getBooksCount();

        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
        verify(bookRepository, times(1)).count();
    }

    @Test
    @DisplayName("добавлять книгу в БД при указании ее названия и идентификаторов жанра и автора")
    void shouldInsertBookWithTitleAndIdentifiers() {
        var expectedGenre = new Genre(1, "Жанр_01");
        var expectedAuthor = new Author(1, "Автор_01");
        var expectedBook = new Book(4, "Книга_04", expectedGenre, expectedAuthor);

        given(genreService.getGenreById(expectedGenre.getId())).willReturn(expectedGenre);
        given(authorService.getAuthorById(expectedAuthor.getId())).willReturn(expectedAuthor);
        given(bookRepository.insert(new Book(0, expectedBook.getTitle(), expectedGenre, expectedAuthor)))
                .willReturn(expectedBook);

        var actualBook = bookService.addBook(expectedBook.getTitle(), expectedGenre.getId(), expectedAuthor.getId());

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
        verify(genreService, times(1))
                .getGenreById(expectedGenre.getId());
        verify(authorService, times(1))
                .getAuthorById(expectedAuthor.getId());
        verify(bookRepository, times(1))
                .insert(new Book(0, expectedBook.getTitle(), expectedGenre, expectedAuthor));
    }

    @Test
    @DisplayName("добавлять книгу в БД при указании ее названия, названия жанра и имени автора")
    void shouldInsertBookWithNames() {
        var expectedGenre = new Genre(1, "Жанр_01");
        var expectedAuthor = new Author(1, "Автор_01");
        var expectedBook = new Book(4, "Книга_04", expectedGenre, expectedAuthor);

        given(genreService.getGenreByName(expectedGenre.getGenreName())).willReturn(expectedGenre);
        given(authorService.getAuthorByName(expectedAuthor.getName())).willReturn(expectedAuthor);
        given(bookRepository.insert(new Book(0, expectedBook.getTitle(), expectedGenre, expectedAuthor)))
                .willReturn(expectedBook);

        var actualBook = bookService.addBook(expectedBook.getTitle(), expectedGenre.getGenreName(),
                expectedAuthor.getName());

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
        verify(genreService, times(1))
                .getGenreByName(expectedGenre.getGenreName());
        verify(authorService, times(1))
                .getAuthorByName(expectedAuthor.getName());
        verify(bookRepository, times(1))
                .insert(new Book(0, expectedBook.getTitle(), expectedGenre, expectedAuthor));
    }

    @Test
    @DisplayName("изменять имеющуюся в БД книгу при указании ее id, названия и идентификаторов жанра и автора")
    void shouldChangeBookWithTitleAndIdentifiers() {
        var bookFromDatabase = new Book(EXISTING_BOOK_ID, "Книга_01", new Genre(1, "Жанр_01"),
                new Author(3, "Автор_03"));

        var expectedGenre = new Genre(2, "Жанр_02");
        var expectedAuthor = new Author(1, "Автор_01");
        var expectedChangedBook = new Book(EXISTING_BOOK_ID, "Книга_01_Updated", expectedGenre, expectedAuthor);

        given(genreService.getGenreById(expectedGenre.getId())).willReturn(expectedGenre);
        given(authorService.getAuthorById(expectedAuthor.getId())).willReturn(expectedAuthor);
        given(bookRepository.findById(expectedChangedBook.getId())).willReturn(Optional.of(bookFromDatabase));
        given(bookRepository.update(expectedChangedBook)).willReturn(expectedChangedBook);

        var actualChangedBook = bookService.changeBook(expectedChangedBook.getId(), expectedChangedBook.getTitle(),
                expectedGenre.getId(), expectedAuthor.getId());

        assertThat(actualChangedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedChangedBook);
        verify(genreService, times(1)).getGenreById(expectedGenre.getId());
        verify(authorService, times(1)).getAuthorById(expectedAuthor.getId());
        verify(bookRepository, times(1)).findById(expectedChangedBook.getId());
        verify(bookRepository, times(1)).update(expectedChangedBook);
    }

    @Test
    @DisplayName("изменять имеющуюся в БД книгу при указании ее id, названия, названия жанра и имени автора")
    void shouldChangeBookWithNames() {
        var bookFromDatabase = new Book(EXISTING_BOOK_ID, "Книга_01", new Genre(1, "Жанр_01"),
                new Author(3, "Автор_03"));

        var expectedGenre = new Genre(2, "Жанр_02");
        var expectedAuthor = new Author(1, "Автор_01");
        var expectedChangedBook = new Book(EXISTING_BOOK_ID, "Книга_01_Updated", expectedGenre, expectedAuthor);

        given(genreService.getGenreByName(expectedGenre.getGenreName())).willReturn(expectedGenre);
        given(authorService.getAuthorByName(expectedAuthor.getName())).willReturn(expectedAuthor);
        given(bookRepository.findById(expectedChangedBook.getId())).willReturn(Optional.of(bookFromDatabase));
        given(bookRepository.update(expectedChangedBook)).willReturn(expectedChangedBook);

        var actualChangedBook = bookService.changeBook(expectedChangedBook.getId(), expectedChangedBook.getTitle(),
                expectedGenre.getGenreName(), expectedAuthor.getName());

        assertThat(actualChangedBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedChangedBook);
        verify(genreService, times(1)).getGenreByName(expectedGenre.getGenreName());
        verify(authorService, times(1)).getAuthorByName(expectedAuthor.getName());
        verify(bookRepository, times(1)).findById(expectedChangedBook.getId());
        verify(bookRepository, times(1)).update(expectedChangedBook);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по идентификатору")
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book();
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setTitle("Книга_01");
        expectedBook.setGenre(new Genre(1, "Жанр_01"));
        expectedBook.setAuthor(new Author(3, "Автор_03"));

        given(bookRepository.findById(EXISTING_BOOK_ID)).willReturn(Optional.of(expectedBook));
        given(bookRepository.findById(NOT_EXISTING_BOOK_ID)).willReturn(Optional.empty());

        var actualBook = bookService.getBookById(EXISTING_BOOK_ID);

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
        assertThatThrownBy(() -> bookService.getBookById(NOT_EXISTING_BOOK_ID))
                .isInstanceOf(IllegalArgumentException.class);
        verify(bookRepository, times(1)).findById(EXISTING_BOOK_ID);
        verify(bookRepository, times(1)).findById(NOT_EXISTING_BOOK_ID);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг")
    void shouldReturnExpectedBooksList() {
        var expectedBooks = List.of(
                new Book(1, "Книга_01", new Genre(1, "Жанр_01"),
                        new Author(3, "Автор_03")),
                new Book(2, "Книга_02", new Genre(2, "Жанр_02"),
                        new Author(1, "Автор_01")),
                new Book(3, "Книга_03", new Genre(1, "Жанр_01"),
                        new Author(2, "Автор_02"))
        );

        given(bookRepository.findAll()).willReturn(expectedBooks);
        var actualBooksList = bookService.getAllBooks();

        assertThat(actualBooksList)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного жанра")
    void shouldReturnBooksListByExpectedGenre() {
        var expectedGenre = new Genre(1, "Жанр_01");
        var expectedBooksByGenre = List.of(
                new Book(1, "Книга_01", expectedGenre, new Author(3, "Автор_03")),
                new Book(3, "Книга_03", expectedGenre, new Author(2, "Автор_02"))
        );

        given(bookRepository.findAllByGenre(expectedGenre.getGenreName())).willReturn(expectedBooksByGenre);
        var actualBooksByGenre = bookService.getAllBooksByGenre(expectedGenre.getGenreName());

        assertThat(actualBooksByGenre)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooksByGenre);
        verify(bookRepository, times(1)).findAllByGenre(expectedGenre.getGenreName());
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного автора")
    void shouldReturnBooksListByExpectedAuthor() {
        var expectedAuthor = new Author(1, "Автор_01");
        var expectedBooksByAuthor = List.of(new Book(2, "Книга_02",
                new Genre(2, "Жанр_02"), expectedAuthor));

        given(bookRepository.findAllByAuthor(expectedAuthor.getName())).willReturn(expectedBooksByAuthor);
        var actualBooksByAuthor = bookService.getAllBooksByAuthor(expectedAuthor.getName());

        assertThat(actualBooksByAuthor)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooksByAuthor);
        verify(bookRepository, times(1)).findAllByAuthor(expectedAuthor.getName());
    }

    @Test
    @DisplayName("удалять заданную книгу по ее идентификатору")
    void shouldDeleteBookById() {
        var expectedBook = new Book();
        expectedBook.setId(EXISTING_BOOK_ID);
        expectedBook.setTitle("Книга_01");
        expectedBook.setGenre(new Genre(1, "Жанр_01"));
        expectedBook.setAuthor(new Author(3, "Автор_03"));

        given(bookRepository.findById(EXISTING_BOOK_ID)).willReturn(Optional.of(expectedBook));

        var actualBook = bookService.deleteBookById(EXISTING_BOOK_ID);

        assertThat(actualBook)
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
        verify(bookRepository, times(1)).deleteById(EXISTING_BOOK_ID);
    }

    @Test
    @DisplayName("удалять все книги из БД")
    void shouldDeleteAllBooks() {
        given(bookRepository.deleteAll()).willReturn(EXPECTED_DELETED_BOOKS_COUNT);
        var actualDeletedBooksCount = bookService.deleteAllBooks();

        assertThat(actualDeletedBooksCount).isEqualTo(EXPECTED_DELETED_BOOKS_COUNT);
        verify(bookRepository, times(1)).deleteAll();
    }
}