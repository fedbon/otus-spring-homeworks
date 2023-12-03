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

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест AuthorRepositoryJpa должен")
@DataJpaTest
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
class AuthorRepositoryTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;

    private static final long EXPECTED_AUTHORS_COUNT_AFTER_CLEANING = 0;

    private static final long EXISTING_AUTHOR_ID = 1L;

    private static final String NEW_AUTHOR_NAME = "Автор_04";

    private static final String UPDATED_AUTHOR_NAME = "Автор_01_updated";

    @Autowired
    private TestEntityManager testEntityManager;

    @Autowired
    private AuthorRepository authorRepository;

    @Test
    @DisplayName("добавлять автора в БД")
    void shouldInsertAuthor() {
        var expectedAuthor = new Author();
        expectedAuthor.setName(NEW_AUTHOR_NAME);

        authorRepository.save(expectedAuthor);
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
    @DisplayName("изменять имеющегося в БД автора при отключении объекта автора от контекста")
    void shouldUpdateAuthorWithDetaching() {
        var author = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        testEntityManager.detach(author);
        author.setName(UPDATED_AUTHOR_NAME);
        authorRepository.save(author);
        var updatedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);

        assertThat(updatedAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его идентификатору")
    void shouldReturnExpectedAuthorById() {
        var expectedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        var optionalActualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID);

        assertThat(optionalActualAuthor)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("возвращать ожидаемый список авторов")
    void shouldReturnExpectedAuthorList() {
        var expectedAuthors = List.of(
                testEntityManager.find(Author.class, 1L),
                testEntityManager.find(Author.class, 2L),
                testEntityManager.find(Author.class, 3L)
        );
        var actualAuthors = authorRepository.findAll(Sort.by(Sort.Direction.ASC,"id"));

        assertThat(actualAuthors).isNotNull().hasSize(EXPECTED_AUTHORS_COUNT)
                .usingRecursiveFieldByFieldElementComparator()
                .containsExactlyElementsOf(expectedAuthors);
    }

    @Test
    @DisplayName("удалять заданного автора по его идентификатору")
    void shouldDeleteAuthorById() {
        var existingAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(existingAuthor).isNotNull();

        authorRepository.deleteById(EXISTING_AUTHOR_ID);

        var deletedAuthor = testEntityManager.find(Author.class, EXISTING_AUTHOR_ID);
        assertThat(deletedAuthor).isNull();
    }
}