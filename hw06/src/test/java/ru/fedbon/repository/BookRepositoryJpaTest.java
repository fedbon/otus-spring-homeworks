package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест BookRepositoryJpa должен")
@DataJpaTest
@Import(value = {BookRepositoryJpa.class})
class BookRepositoryJpaTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    private static final String NEW_BOOK_TITLE = "Книга_04";

    private static final long TEST_GENRE_ID = 1;

    private static final long TEST_AUTHOR_ID = 1;

    private static final long EXISTING_BOOK_ID = 1;

    private static final String UPDATED_BOOK_TITLE = "Книга_04_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepositoryJpa bookRepositoryJpa;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBooksCount() {
        var actualBooksCount = bookRepositoryJpa.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("добавлять книгу в БД")
    void shouldInsertBook() {
        var newBook = new Book();
        newBook.setTitle(NEW_BOOK_TITLE);
        var testGenre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
        newBook.setGenre(testGenre);
        var testAuthor = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
        newBook.setAuthor(testAuthor);

        var expectedBook = bookRepositoryJpa.save(newBook);
        assertThat(expectedBook).isNotNull();
        assertThat(expectedBook.getId()).isGreaterThan(0);

        var actualBook = testEntityManager.find(Book.class, expectedBook.getId());

        assertThat(actualBook)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);

        assertThat(actualBook)
                .matches(book -> book.getId() == 4)
                .matches(book -> book.getTitle().equals(NEW_BOOK_TITLE))
                .matches(book -> book.getGenre() != null
                        && book.getGenre().getId() == TEST_GENRE_ID)
                .matches(book -> book.getAuthor() != null
                        && book.getAuthor().getId() == TEST_AUTHOR_ID);
    }

    @Test
    @DisplayName("изменять имеющуюся в БД книгу")
    void shouldUpdateBook() {
        var book = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        book.setTitle(UPDATED_BOOK_TITLE);
        testEntityManager.flush();
        var updatedBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);

        assertThat(updatedBook.getTitle()).isEqualTo(UPDATED_BOOK_TITLE);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по идентификатору")
    void shouldReturnExpectedBookById() {
        var optionalActualBook = bookRepositoryJpa.findById(EXISTING_BOOK_ID);
        var expectedBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);

        assertThat(optionalActualBook)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedBook);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг")
    void shouldReturnExpectedBooksList() {
        var books = bookRepositoryJpa.findAll();

        assertThat(books).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .allMatch(book -> !book.getTitle().isEmpty())
                .allMatch(book -> book.getGenre() != null)
                .allMatch(book -> book.getAuthor() != null)
                .containsOnlyOnce(testEntityManager.find(Book.class, EXISTING_BOOK_ID));
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного жанра")
    void shouldReturnBooksListByExpectedGenre() {
        var expectedGenre = new Genre(TEST_GENRE_ID, "Жанр_01");
        var expectedBooksByGenre = List.of(
                new Book(1L, "Книга_01", expectedGenre, new Author(3L, "Автор_03")),
                new Book(3L, "Книга_03", expectedGenre, new Author(2L, "Автор_02"))
        );

        var actualBooksByGenre = bookRepositoryJpa.findAllByGenre(expectedGenre);

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

        var actualBooksByAuthor = bookRepositoryJpa.findAllByAuthor(expectedAuthor);

        assertThat(actualBooksByAuthor)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooksByAuthor);
    }

    @Test
    @DisplayName("удалять заданную книгу по ее идентификатору")
    void shouldDeleteBookById() {
        var existingBook = bookRepositoryJpa.findById(1L);
        assertThat(existingBook).isNotEmpty();

        bookRepositoryJpa.deleteById(1L);

        var deletedBook = bookRepositoryJpa.findById(1L);
        assertThat(deletedBook).isEmpty();
    }

    @Test
    @DisplayName("удалять все книги из БД")
    void shouldDeleteAllBooks() {
        var actualCountBeforeCleaning = bookRepositoryJpa.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(3);

        bookRepositoryJpa.deleteAll();

        var expectedCountAfterCleaning = 0;
        var actualCountAfterCleaning = bookRepositoryJpa.count();
        assertThat(actualCountAfterCleaning).isEqualTo(expectedCountAfterCleaning);
    }
}