package ru.fedbon.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.fedbon.model.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест AuthorRepositoryJpa должен")
@DataJpaTest
@Import(value = {AuthorRepositoryJpa.class})
class AuthorRepositoryJpaTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;

    private static final long EXPECTED_AUTHORS_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String NEW_AUTHOR_NAME = "Автор_04";

    private static final String UPDATED_AUTHOR_NAME = "Автор_01_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepositoryJpa authorRepositoryJpa;

    @Test
    @DisplayName("возвращать ожидаемое число авторов в БД")
    void shouldReturnExpectedAuthorsCount() {
        var actualAuthorsCount = authorRepositoryJpa.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("добавлять автора в БД")
    void shouldInsertAuthor() {
        var expectedAuthor = new Author();
        expectedAuthor.setName(NEW_AUTHOR_NAME);

        authorRepositoryJpa.save(expectedAuthor);
        assertThat(expectedAuthor.getId()).isGreaterThan(0);

        var actualAuthor = testEntityManager.find(Author.class, expectedAuthor.getId());

        assertThat(actualAuthor)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        assertThat(actualAuthor)
                .matches(author -> author.getId() == 4)
                .matches(author -> author.getName().equals(NEW_AUTHOR_NAME));
    }

    @Test
    @DisplayName("изменять имеющегося в БД автора")
    void shouldUpdateAuthor() {
        var author = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        author.setName(UPDATED_AUTHOR_NAME);
        testEntityManager.flush();
        var updatedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);

        assertThat(updatedAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его идентификатору")
    void shouldReturnExpectedAuthorById() {
        var expectedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        var optionalActualAuthor = authorRepositoryJpa.findById(EXISTING_AUTHOR_ID);

        assertThat(optionalActualAuthor)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать ожидаемый список авторов")
    void shouldReturnExpectedAuthorList() {
        var authors = authorRepositoryJpa.findAll();

        assertThat(authors).isNotNull().hasSize(EXPECTED_AUTHORS_COUNT)
                .allMatch(author -> !author.getName().isEmpty())
                .containsOnlyOnce(testEntityManager.find(Author.class, EXISTING_AUTHOR_ID));
    }

    @Test
    @DisplayName("удалять заданного автора по его идентификатору")
    void shouldDeleteAuthorById() {
        var existingAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(existingAuthor).isNotNull();

        authorRepositoryJpa.deleteById(EXISTING_AUTHOR_ID);

        var deletedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }

    @Test
    @DisplayName("удалять всех авторов из БД")
    void shouldDeleteAllAuthors() {
        var actualCountBeforeCleaning = authorRepositoryJpa.count();
        assertThat(actualCountBeforeCleaning).isEqualTo(EXPECTED_AUTHORS_COUNT);

        authorRepositoryJpa.deleteAll();

        var actualCountAfterCleaning = authorRepositoryJpa.count();
        assertThat(actualCountAfterCleaning).isEqualTo(EXPECTED_AUTHORS_COUNT_AFTER_CLEANING);
    }
}