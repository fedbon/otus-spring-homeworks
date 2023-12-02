package ru.fedbon.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.fedbon.model.Author;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Тест AuthorRepository должен")
@DataMongoTest
class AuthorRepositoryTest {

    private static final int EXPECTED_AUTHORS_COUNT = 3;

    private static final String EXISTING_AUTHOR_ID = "1";

    private static final String NEW_AUTHOR_NAME = "Автор_04";

    private static final String UPDATED_AUTHOR_NAME = "Автор_01_updated";

    private List<Author> authors;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AuthorRepository authorRepository;

    @BeforeEach
    void setUp() {
        authors = mongoTemplate.findAll(Author.class);
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.getDb().drop();
        mongoTemplate.insertAll(authors);
    }

    @Test
    @DisplayName("возвращать ожидаемое число авторов в БД")
    void shouldReturnExpectedAuthorsCount() {
        var actualAuthorsCount = authorRepository.count();
        assertThat(actualAuthorsCount).isEqualTo(EXPECTED_AUTHORS_COUNT);
    }

    @Test
    @DisplayName("добавлять автора в БД")
    void shouldInsertAuthor() {
        var expectedAuthor = new Author();
        expectedAuthor.setName(NEW_AUTHOR_NAME);

        var savedAuthor = authorRepository.save(expectedAuthor);

        assertThat(savedAuthor.getId()).isNotNull();

        var actualAuthor = mongoTemplate.findById(savedAuthor.getId(), Author.class);

        assertThat(actualAuthor)
                .isNotNull()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);

        assertThat(actualAuthor)
                .matches(author -> Objects.equals(author.getId(), savedAuthor.getId()))
                .matches(author -> author.getName().equals(NEW_AUTHOR_NAME));
    }

    @Test
    @DisplayName("изменять имеющегося в БД автора")
    void shouldUpdateAuthor() {
        var existingAuthor = mongoTemplate.findById(EXISTING_AUTHOR_ID, Author.class);
        assertThat(existingAuthor).isNotNull();

        existingAuthor.setName(UPDATED_AUTHOR_NAME);
        authorRepository.save(existingAuthor);

        var updatedAuthor = mongoTemplate.findById(EXISTING_AUTHOR_ID, Author.class);
        assertThat(updatedAuthor).isNotNull();

        assertThat(updatedAuthor.getName()).isEqualTo(UPDATED_AUTHOR_NAME);
    }

    @Test
    @DisplayName("возвращать ожидаемого автора по его идентификатору")
    void shouldReturnExpectedAuthorById() {
        var expectedAuthor = mongoTemplate.findById(EXISTING_AUTHOR_ID, Author.class);
        var optionalActualAuthor = authorRepository.findById(EXISTING_AUTHOR_ID);

        assertThat(optionalActualAuthor)
                .isPresent()
                .get()
                .usingRecursiveComparison()
                .isEqualTo(expectedAuthor);
    }

    @Test
    @DisplayName("удалять заданного автора по его идентификатору")
    void shouldDeleteAuthorById() {
        var existingAuthor = mongoTemplate.findById(EXISTING_AUTHOR_ID, Author.class);
        assertThat(existingAuthor).isNotNull();

        authorRepository.deleteById(EXISTING_AUTHOR_ID);

        var deletedAuthor = mongoTemplate.findById(EXISTING_AUTHOR_ID, Author.class);
        assertThat(deletedAuthor).isNull();
    }

    @Test
    @DisplayName("удалять всех авторов из БД")
    void shouldDeleteAllAuthors() {
        var existingAuthors = mongoTemplate.findAll(Author.class);

        assertThat(existingAuthors).isNotNull().hasSize(3);

        authorRepository.deleteAll();

        var deletedAuthors = mongoTemplate.findAll(Author.class);

        assertThat(deletedAuthors).isNotNull().isEmpty();
    }
}