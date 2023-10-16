package ru.fedbon.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Author;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест AuthorRepositoryJdbc должен")
@JdbcTest
@Import(value = {AuthorRepositoryJdbc.class})
class AuthorRepositoryJdbcTest {

    private static final long EXPECTED_AUTHORS_COUNT = 3;

    private static final long EXPECTED_AUTHORS_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_AUTHOR_ID = 1;

    private static final String EXISTING_AUTHOR_NAME = "Автор_01";

    private static final String NEW_AUTHOR_NAME = "Автор_04";

    private static final String UPDATED_AUTHOR_NAME = "Автор_01_updated";

    @Autowired
    private AuthorRepositoryJdbc authorRepositoryJdbc;

    @Test
    @DisplayName("возвращать ожидаемое число авторов в БД")
    void shouldReturnExpectedAuthorsCount() {
        var actualAuthorsCount = authorRepositoryJdbc.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("добавлять автора в БД")
    void shouldInsertAuthor() {
        var expectedAuthor = new Author();
        expectedAuthor.setName(NEW_AUTHOR_NAME);

        authorRepositoryJdbc.insert(expectedAuthor);
        var actualAuthor = authorRepositoryJdbc.findById(expectedAuthor.getId());

        Assertions.assertThat(actualAuthor)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("изменять имеющегося в БД автора")
    void shouldUpdateAuthor() {
        var expectedAuthor = new Author(EXISTING_AUTHOR_ID, UPDATED_AUTHOR_NAME);

        authorRepositoryJdbc.update(expectedAuthor);
        var actualAuthor = authorRepositoryJdbc.findById(expectedAuthor.getId());

        Assertions.assertThat(actualAuthor)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его идентификатору")
    void shouldReturnExpectedAuthorById() {
        var expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);

        var actualAuthor = authorRepositoryJdbc.findById(expectedAuthor.getId());

        Assertions.assertThat(actualAuthor)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по имени")
    void shouldReturnExpectedAuthorByName() {
        var expectedAuthor = new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME);

        var actualAuthor = authorRepositoryJdbc.findByName(expectedAuthor.getName());

        Assertions.assertThat(actualAuthor)
                .isNotEmpty()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать ожидаемый список авторов")
    void shouldReturnExpectedAuthorList() {
        var expectedAuthors = List.of(
                new Author(EXISTING_AUTHOR_ID, EXISTING_AUTHOR_NAME),
                new Author(EXISTING_AUTHOR_ID + 1, EXISTING_AUTHOR_NAME.replace('1', '2')),
                new Author(EXISTING_AUTHOR_ID + 2, EXISTING_AUTHOR_NAME.replace('1', '3'))
        );

        var actualAuthors = authorRepositoryJdbc.findAll();

        Assertions.assertThat(actualAuthors)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyInAnyOrderElementsOf(expectedAuthors);
    }

    @Test
    @DisplayName("удалять заданного автора по его идентификатору")
    void shouldDeleteAuthorById() {
        var existingAuthor = authorRepositoryJdbc.findById(EXISTING_AUTHOR_ID);
        Assertions.assertThat(existingAuthor).isNotEmpty();

        authorRepositoryJdbc.deleteById(EXISTING_AUTHOR_ID);

        var deletedAuthor = authorRepositoryJdbc.findById(EXISTING_AUTHOR_ID);
        Assertions.assertThat(deletedAuthor).isEmpty();
    }

    @Test
    @DisplayName("удалять всех авторов из БД")
    void shouldDeleteAllAuthors() {
        var actualCountBeforeCleaning = authorRepositoryJdbc.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(EXPECTED_AUTHORS_COUNT);

        authorRepositoryJdbc.deleteAll();

        var actualCountAfterCleaning = authorRepositoryJdbc.count();
        assertThat(actualCountAfterCleaning).isEqualTo(EXPECTED_AUTHORS_COUNT_AFTER_CLEANING);
    }
}