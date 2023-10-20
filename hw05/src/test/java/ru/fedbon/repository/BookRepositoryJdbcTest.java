package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест BookRepositoryJdbc должен")
@JdbcTest
@Import(value = {BookRepositoryJdbc.class})
class BookRepositoryJdbcTest {

    @Autowired
    private BookRepositoryJdbc bookRepositoryJdbc;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBooksCount() {
        var actualBooksCount = bookRepositoryJdbc.count();
        assertThat(actualBooksCount).isEqualTo(3);
    }

    @Test
    @DisplayName("добавлять книгу в БД")
    void shouldInsertBook() {
        var expectedBook = new Book();
        expectedBook.setTitle("Книга_04");
        expectedBook.setGenre(new Genre(1L, "Жанр_01"));
        expectedBook.setAuthor(new Author(1L, "Автор_01"));

        bookRepositoryJdbc.insert(expectedBook);
        var actualBook = bookRepositoryJdbc.findById(expectedBook.getId());

        assertThat(actualBook)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("изменять имеющуюся в БД книгу")
    void shouldUpdateBook() {
        var expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setTitle("Книга_01_updated");
        expectedBook.setGenre(new Genre(2L, "Жанр_02"));
        expectedBook.setAuthor(new Author(1L, "Автор_01"));

        bookRepositoryJdbc.update(expectedBook);
        var actualBook = bookRepositoryJdbc.findById(expectedBook.getId());

        assertThat(actualBook)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по идентификатору")
    void shouldReturnExpectedBookById() {
        var expectedBook = new Book();
        expectedBook.setId(1L);
        expectedBook.setTitle("Книга_01");
        expectedBook.setGenre(new Genre(1L, "Жанр_01"));
        expectedBook.setAuthor(new Author(3L, "Автор_03"));

        var actualBook = bookRepositoryJdbc.findById(expectedBook.getId());

        assertThat(actualBook)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
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

        var actualBooks = bookRepositoryJdbc.findAll();

        assertThat(actualBooks)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного жанра")
    void shouldReturnBooksListByExpectedGenre() {
        var expectedGenre = new Genre(1L, "Жанр_01");
        var expectedBooksByGenre = List.of(
                new Book(1L, "Книга_01", expectedGenre, new Author(3L, "Автор_03")),
                new Book(3L, "Книга_03", expectedGenre, new Author(2L, "Автор_02"))
        );

        var actualBooksByGenre = bookRepositoryJdbc.findAllByGenre(expectedGenre.getGenreName());

        assertThat(actualBooksByGenre)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooksByGenre);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного автора")
    void shouldReturnBooksListByExpectedAuthor() {
        var expectedAuthor = new Author(1L, "Автор_01");
        var expectedBooksByAuthor = List.of(new Book(2L, "Книга_02",
                new Genre(2L, "Жанр_02"), expectedAuthor));

        var actualBooksByAuthor = bookRepositoryJdbc.findAllByAuthor(expectedAuthor.getName());

        assertThat(actualBooksByAuthor)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooksByAuthor);
    }

    @Test
    @DisplayName("удалять заданную книгу по ее идентификатору")
    void shouldDeleteBookById() {
        var existingBook = bookRepositoryJdbc.findById(1L);
        assertThat(existingBook).isNotEmpty();

        bookRepositoryJdbc.deleteById(1L);

        var deletedBook = bookRepositoryJdbc.findById(1L);
        assertThat(deletedBook).isEmpty();
    }

    @Test
    @DisplayName("удалять все книги из БД")
    void shouldDeleteAllBooks() {
        var actualCountBeforeCleaning = bookRepositoryJdbc.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(3);

        bookRepositoryJdbc.deleteAll();

        var expectedCountAfterCleaning = 0;
        var actualCountAfterCleaning = bookRepositoryJdbc.count();
        assertThat(actualCountAfterCleaning).isEqualTo(expectedCountAfterCleaning);
    }
}