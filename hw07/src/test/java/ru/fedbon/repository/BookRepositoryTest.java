package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import ru.fedbon.model.Author;
import ru.fedbon.model.Book;
import ru.fedbon.model.Genre;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест BookRepositoryJpa должен")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class BookRepositoryTest {

    private static final int EXPECTED_BOOKS_COUNT = 3;

    private static final long EXPECTED_BOOKS_COUNT_AFTER_CLEANING = 0;

    private static final String NEW_BOOK_TITLE = "Книга_04";

    private static final long TEST_GENRE_ID = 1;

    private static final long TEST_AUTHOR_ID = 1;

    private static final long EXISTING_BOOK_ID = 1;

    private static final String UPDATED_BOOK_TITLE = "Книга_04_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    @DisplayName("возвращать ожидаемое количество книг в БД")
    void shouldReturnExpectedBooksCount() {
        var actualBooksCount = bookRepository.count();
        assertThat(actualBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);
    }

    @Test
    @DisplayName("добавлять книгу в БД")
    void shouldInsertBook() {
        var expectedBook = new Book();
        expectedBook.setTitle(NEW_BOOK_TITLE);
        var testGenre = testEntityManager.find(Genre.class, TEST_GENRE_ID);
        expectedBook.setGenre(testGenre);
        var testAuthor = testEntityManager.find(Author.class, TEST_AUTHOR_ID);
        expectedBook.setAuthor(testAuthor);

        bookRepository.save(expectedBook);
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
    @DisplayName("изменять имеющуюся в БД книгу при отключении объекта книги от контекста")
    void shouldUpdateBookWithDetaching() {
        var book = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        testEntityManager.detach(book);
        book.setTitle(UPDATED_BOOK_TITLE);
        bookRepository.save(book);
        var updatedBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);

        assertThat(updatedBook.getTitle()).isEqualTo(UPDATED_BOOK_TITLE);
    }

    @Test
    @DisplayName("возвращать ожидаемую книгу по идентификатору")
    void shouldReturnExpectedBookById() {
        var optionalActualBook = bookRepository.findById(EXISTING_BOOK_ID);
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
        var expectedBooks = List.of(
                testEntityManager.find(Book.class, 1L),
                testEntityManager.find(Book.class, 2L),
                testEntityManager.find(Book.class, 3L)
        );
        var actualBooks = bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));

        assertThat(actualBooks).isNotNull().hasSize(EXPECTED_BOOKS_COUNT)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного жанра")
    void shouldReturnBooksListByExpectedGenre() {
        var expectedBooks = List.of(
                testEntityManager.find(Book.class, 1L),
                testEntityManager.find(Book.class, 3L)
        );
        var expectedGenre = testEntityManager.find(Genre.class, 1L);

        var actualBooks = bookRepository.findAllByGenre(expectedGenre);

        assertThat(actualBooks).isNotNull().hasSize(2)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("возвращать ожидаемый список книг одного автора")
    void shouldReturnBooksListByExpectedAuthor() {
        var expectedBooks = List.of(
                testEntityManager.find(Book.class, 1L)
        );
        var expectedAuthor = testEntityManager.find(Author.class, 3L);

        var actualBooks = bookRepository.findAllByAuthor(expectedAuthor);

        assertThat(actualBooks).isNotNull().hasSize(1)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    @DisplayName("удалять заданную книгу по ее идентификатору")
    void shouldDeleteBookById() {
        var existingBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        assertThat(existingBook).isNotNull();

        bookRepository.deleteById(EXISTING_BOOK_ID);

        var deletedBook = testEntityManager.find(Book.class, EXISTING_BOOK_ID);
        assertThat(deletedBook).isNull();
    }

    @Test
    @DisplayName("удалять все книги из БД")
    void shouldDeleteAllBooks() {
        var actualCountBeforeCleaning = bookRepository.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(EXPECTED_BOOKS_COUNT);

        var deletedBooksCount = bookRepository.deleteAllCustom();

        assertThat(deletedBooksCount).isEqualTo(EXPECTED_BOOKS_COUNT);

        var actualCountAfterCleaning = bookRepository.count();
        assertThat(actualCountAfterCleaning).isEqualTo(EXPECTED_BOOKS_COUNT_AFTER_CLEANING);
    }
}